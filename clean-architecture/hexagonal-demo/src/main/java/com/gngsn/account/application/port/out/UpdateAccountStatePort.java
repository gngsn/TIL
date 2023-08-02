package com.gngsn.account.application.port.out;

import com.gngsn.account.application.domain.model.Account;

public interface UpdateAccountStatePort {
    void updateActivities(Account account);
}
