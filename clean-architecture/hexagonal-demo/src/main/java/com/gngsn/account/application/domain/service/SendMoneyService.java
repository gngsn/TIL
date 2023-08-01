package com.gngsn.account.application.domain.service;

import com.gngsn.account.application.port.in.SendMoneyCommand;
import com.gngsn.account.application.port.in.SendMoneyUseCase;
import com.gngsn.account.application.port.out.AccountLock;
import com.gngsn.account.application.port.out.LoadAccountPort;
import com.gngsn.account.application.port.out.UpdateAccountStatePort;
import com.gngsn.account.common.UseCase;
import jakarta.transaction.Transactional;

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
        this.moneyTransferProperties = moneyTransferProperties;
    }

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // 비즈니스 규칙 검증
        checkThreshold(command);

        // TODO: 모델 상태 조작
        // TODO: 출력 값 반환
        return false;
    }

    private void checkThreshold(SendMoneyCommand command) {
        if(command.money().isGreaterThan(moneyTransferProperties.getMaximumTransferThreshold())){
            throw new ThresholdExceededException(moneyTransferProperties.getMaximumTransferThreshold(), command.money());
        }
    }
}
