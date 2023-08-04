package com.gngsn.account.application.domain.common;

import com.gngsn.account.application.domain.model.Money;
import lombok.Data;
import lombok.Getter;

@Getter
public class MoneyTransferProperties {
    private final Money maximumTransferThreshold = Money.of(1_000_000L);
}
