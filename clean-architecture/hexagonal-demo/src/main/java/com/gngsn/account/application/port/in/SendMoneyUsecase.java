package com.gngsn.account.application.port.in;

public interface SendMoneyUsecase {
    boolean sendMoney(SendMoneyCommand command);
}
