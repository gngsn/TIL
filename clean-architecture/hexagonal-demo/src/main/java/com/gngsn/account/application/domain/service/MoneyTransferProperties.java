package com.gngsn.account.application.domain.service;

import com.gngsn.account.application.domain.model.Money;
import lombok.Getter;
import lombok.Setter;

public record MoneyTransferProperties(
        @Getter Money maximumTransferThreshold
) {
    public MoneyTransferProperties {
        maximumTransferThreshold = Money.of(1_000_000L);
    }
}
