package com.gngsn.account.application.domain.model;

import com.gngsn.account.application.domain.model.Account.AccountId;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * Activity Entity.
 * - 계좌에 대한 모든 입금과 출금 포착.
 * - 한 계좌에 대한 모든 활동(activity)들을 항상 메모리에 한꺼번에 올리는 것은 효율적이지 않기 때문에, Account 엔티티는 ActiviryWindow 값 객체(value object)에서 포착한 지난 며칠 혹은 몇 주간의 범위에 해당하는 활동만 보유
 */
public class Activity {

    private final ActivityId id;
    private final AccountId ownerAccountId;
    @Getter private final  AccountId sourceAccountId;
    @Getter private final AccountId targetAccountId;
    private final LocalDateTime timestamp;
    @Getter private final Money money;

    public Activity(
            @NonNull AccountId ownerAccountId,
            @NonNull AccountId sourceAccountId,
            @NonNull AccountId targetAccountId,
            @NonNull LocalDateTime timestamp,
            @NonNull Money money) {
        this.id = null;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public Activity(
            @NonNull ActivityId id,
            @NonNull AccountId ownerAccountId,
            @NonNull AccountId sourceAccountId,
            @NonNull AccountId targetAccountId,
            @NonNull LocalDateTime timestamp,
            @NonNull Money money) {
        this.id = id;
        this.ownerAccountId = ownerAccountId;
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.timestamp = timestamp;
        this.money = money;
    }

    public record ActivityId(@Getter Long value) {
    }
}
