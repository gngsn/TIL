package com.gngsn.account.application.domain.service;

import com.gngsn.account.application.domain.common.MoneyTransferProperties;
import com.gngsn.account.application.domain.exception.ThresholdExceededException;
import com.gngsn.account.application.domain.model.Account;
import com.gngsn.account.application.domain.model.Account.AccountId;
import com.gngsn.account.application.port.in.SendMoneyCommand;
import com.gngsn.account.application.port.in.SendMoneyUseCase;
import com.gngsn.account.application.port.out.AccountLock;
import com.gngsn.account.application.port.out.LoadAccountPort;
import com.gngsn.account.application.port.out.UpdateAccountStatePort;
import com.gngsn.account.common.UseCase;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@UseCase
@Transactional
public class SendMoneyService implements SendMoneyUseCase {
    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;
    private final MoneyTransferProperties moneyTransferProperties;

    public SendMoneyService(final LoadAccountPort loadAccountPort, final AccountLock accountLock, final UpdateAccountStatePort updateAccountStatePort, final MoneyTransferProperties moneyTransferProperties) {
        this.loadAccountPort = loadAccountPort;
        this.accountLock = accountLock;
        this.updateAccountStatePort = updateAccountStatePort;
        this.moneyTransferProperties = new MoneyTransferProperties();
    }

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // 비즈니스 규칙 검증
        checkThreshold(command);

        // 모델 상태 조작
        LocalDateTime baselineDate = LocalDateTime.now().minusDays(10);

        Account sourceAccount = loadAccountPort.loadAccount(
                command.sourceAccountId(),
                baselineDate);

        Account targetAccount = loadAccountPort.loadAccount(
                command.targetAccountId(),
                baselineDate);

        AccountId sourceAccountId = sourceAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected source account ID not to be empty"));
        AccountId targetAccountId = targetAccount.getId()
                .orElseThrow(() -> new IllegalStateException("expected target account ID not to be empty"));

        accountLock.lockAccount(sourceAccountId);
        if (!sourceAccount.withdraw(command.money(), targetAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            return false;
        }

        accountLock.lockAccount(targetAccountId);
        if (!targetAccount.deposit(command.money(), sourceAccountId)) {
            accountLock.releaseAccount(sourceAccountId);
            accountLock.releaseAccount(targetAccountId);
            return false;
        }

        updateAccountStatePort.updateActivities(sourceAccount);
        updateAccountStatePort.updateActivities(targetAccount);

        accountLock.releaseAccount(sourceAccountId);
        accountLock.releaseAccount(targetAccountId);
        // 출력 값 반환
        return true;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if(command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
        }
    }
}
