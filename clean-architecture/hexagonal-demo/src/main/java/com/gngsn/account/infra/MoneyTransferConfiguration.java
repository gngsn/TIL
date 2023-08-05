package com.gngsn.account.infra;

import com.gngsn.account.application.domain.common.MoneyTransferProperties;
import com.gngsn.account.application.domain.model.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MoneyTransferConfiguration {

    private final long transferThreshold = Long.MAX_VALUE;

    @Bean
    public MoneyTransferProperties moneyTransferProperties() {
        return new MoneyTransferProperties(Money.of(transferThreshold));
    }
}