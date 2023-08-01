package com.gngsn.account.adapter.out.persistence;

import com.gngsn.account.application.domain.model.Account.AccountId;
import com.gngsn.account.application.port.out.AccountLock;
import org.springframework.stereotype.Component;

@Component
class NoOpAccountLock implements AccountLock {

    @Override
    public void lockAccount(AccountId accountId) {
        // do nothing
    }

    @Override
    public void releaseAccount(AccountId accountId) {
        // do nothing
    }
}
