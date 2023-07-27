package com.gngsn.account.application;

import com.gngsn.account.application.port.in.SendMoneyUsecase;


public class SendMoneyService implements SendMoneyUsecase {
    private final LoadAccountPort loadAccountPort;
    private final AccountLock accountLock;
    private final UpdateAccountStatePort updateAccountStatePort;

    @Override
    public boolean sendMoney(SendMoneyCommand command) {
        // TODO: 비즈니스 규칙 검증
        // TODO: 모델 상태 조작
        // TODO: 출력 값 반환
        return null;
    }
}
