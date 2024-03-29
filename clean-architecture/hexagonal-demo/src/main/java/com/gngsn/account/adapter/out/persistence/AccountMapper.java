package com.gngsn.account.adapter.out.persistence;

import com.gngsn.account.application.domain.model.Account;
import com.gngsn.account.application.domain.model.Account.AccountId;

import com.gngsn.account.application.domain.model.Activity;
import com.gngsn.account.application.domain.model.Activity.ActivityId;
import com.gngsn.account.application.domain.model.ActivityWindow;
import com.gngsn.account.application.domain.model.Money;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper {

    public Account mapToDomainEntity(
            AccountJpaEntity account,
            List<ActivityJpaEntity> activities,
            Long withdrawalBalance,
            Long depositBalance) {

        Money baselineBalance = Money.subtract(
                Money.of(depositBalance),
                Money.of(withdrawalBalance));

        return Account.withId(
                new AccountId(account.getId()),
                mapToActivityWindow(activities),
                baselineBalance);
    }

    public ActivityWindow mapToActivityWindow(List<ActivityJpaEntity> activities) {
        List<Activity> mappedActivities = new ArrayList<>();

        for (ActivityJpaEntity activity : activities) {
            mappedActivities.add(new Activity(
                    new ActivityId(activity.getId()),
                    new AccountId(activity.getOwnerAccountId()),
                    new AccountId(activity.getSourceAccountId()),
                    new AccountId(activity.getTargetAccountId()),
                    activity.getTimestamp(),
                    Money.of(activity.getAmount())));
        }

        return new ActivityWindow(mappedActivities);
    }
}
