package com.gngsn.account.application.domain.service;

import com.gngsn.account.application.port.in.SendMoneyCommand;
import com.gngsn.account.application.port.in.SendMoneyUseCase;
import com.gngsn.account.application.port.out.AccountLock;
import com.gngsn.account.application.port.out.LoadAccountPort;
import com.gngsn.account.application.port.out.UpdateAccountStatePort;
import com.gngsn.account.common.UseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@UseCase
@Transactional
public class SendMoneyService implements SendMoneyUseCase {
    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    public SendMoneyService(final LoadAccountPort loadAccountPort, final AccountLock accountLock, final UpdateAccountStatePort updateAccountStatePort) {
        this.loadAccountPort = loadAccountPort;
        this.accountLock = accountLock;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // TODO: 비즈니스 규칙 검증
        // TODO: 모델 상태 조작
        // TODO: 출력 값 반환
        return false;
    }
}
