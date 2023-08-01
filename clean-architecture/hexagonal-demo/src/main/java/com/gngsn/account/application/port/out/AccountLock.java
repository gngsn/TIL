package com.gngsn.account.application.port.out;

import com.gngsn.account.application.domain.model.Account;

public interface AccountLock {

    void lockAccount(Account.AccountId accountId);

    void releaseAccount(Account.AccountId accountId);
}
