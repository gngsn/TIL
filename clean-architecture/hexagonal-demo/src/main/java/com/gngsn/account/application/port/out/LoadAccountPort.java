package com.gngsn.account.application.port.out;

import com.gngsn.account.application.domain.model.Account;
import com.gngsn.account.application.domain.model.Account.AccountId;

import java.time.LocalDateTime;

public interface LoadAccountPort {
    Account loadAccount(AccountId accountId, LocalDateTime baselineDate);
}
