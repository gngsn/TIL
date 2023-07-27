package com.gngsn.account.domain;

/**
 * Account Entity.
 * - 실제 계좌의 현재 스냅숏.
 */
public class Account {
    private AccountId id;
    private Money vaselineBalance;
    private ActivityWindow activityWindow;

    public Money calculateBalance() {
        return Money.add(
                this.vaselineBalance,
                this.activityWindow.calculateBalance(this.id));
    }

}
