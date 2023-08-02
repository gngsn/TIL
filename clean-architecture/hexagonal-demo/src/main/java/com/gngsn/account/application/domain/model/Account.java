package com.gngsn.account.application.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Account Entity.
 * - 실제 계좌의 현재 스냅숏.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

    private final AccountId id;
    @Getter private final ActivityWindow activityWindow;
    private final Money baselineBalance;

    public Optional<AccountId> getId(){
        return Optional.ofNullable(this.id);
    }

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


    /**
     * Tries to withdraw a certain amount of money from this account.
     * If successful, creates a new activity with a negative value.
     * @return true if the withdrawal was successful, false if not.
     */
    public boolean withdraw(Money money, AccountId targetAccountId) {

        if (!mayWithdraw(money)) {
            return false;
        }

        Activity withdrawal = new Activity(
                this.id,
                this.id,
                targetAccountId,
                LocalDateTime.now(),
                money);
        this.activityWindow.addActivity(withdrawal);
        return true;
    }

    private boolean mayWithdraw(Money money) {
        return Money.add(
                        this.calculateBalance(),
                        money.negate())
                .isPositiveOrZero();
    }


    /**
     * Tries to deposit a certain amount of money to this account.
     * If sucessful, creates a new activity with a positive value.
     * @return true if the deposit was successful, false if not.
     */
    public boolean deposit(Money money, AccountId sourceAccountId) {
        Activity deposit = new Activity(
                this.id,
                sourceAccountId,
                this.id,
                LocalDateTime.now(),
                money);
        this.activityWindow.addActivity(deposit);
        return true;
    }

    public record AccountId(@Getter Long value) {
    }
}
