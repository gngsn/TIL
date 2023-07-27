package com.gngsn.account.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Account Entity.
 * - 실제 계좌의 현재 스냅숏.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    @Getter private final AccountId id;
    @Getter private final ActivityWindow activityWindow;
    private final Money baselineBalance;

    public static Account withoutId(
            ActivityWindow activityWindow,
            Money baselineBalance) {
        return new Account(null, activityWindow, baselineBalance);
    }

    public static Account withId(
            AccountId accountId,
            ActivityWindow activityWindow,
            Money baselineBalance) {
        return new Account(accountId, activityWindow, baselineBalance);
    }

    public Money calculateBalance() {
        return Money.add(
                this.baselineBalance,
                this.activityWindow.calculateBalance(this.id));
    }

    public boolean withdraw(Money money, AccountId targetAccountId) {
        if (!mayWithdraw(money)) {
            return false;
        }
        // ...
    }

    public static class AccountId {
    }
}
