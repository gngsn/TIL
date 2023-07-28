package com.gngsn.account.application;

import com.gngsn.account.application.port.in.SendMoneyCommand;
import com.gngsn.account.application.port.in.SendMoneyUsecase;
import com.gngsn.account.application.port.out.AccountLock;
import com.gngsn.account.application.port.out.LoadAccountPort;
import com.gngsn.account.application.port.out.UpdateAccountStatePort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
public class SendMoneyService implements SendMoneyUsecase {
    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // TODO: 비즈니스 규칙 검증
            // requireAccountExists(command.getSourceAccountId());
            // requireAccountExists(command.getTargetAccountId());
        // TODO: 모델 상태 조작
        // TODO: 출력 값 반환
        return false;
    }
}
