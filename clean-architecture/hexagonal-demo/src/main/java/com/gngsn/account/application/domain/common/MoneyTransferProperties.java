package com.gngsn.account.application.domain.common;

import com.gngsn.account.application.domain.model.Money;
import lombok.Getter;

@Getter
public class MoneyTransferProperties {
    private final Money maximumTransferThreshold;

    public MoneyTransferProperties() {
        this.maximumTransferThreshold = Money.of(1_000_000L);
    }

    public MoneyTransferProperties(Money maximumTransferThreshold) {
        this.maximumTransferThreshold = maximumTransferThreshold;
    }
}
