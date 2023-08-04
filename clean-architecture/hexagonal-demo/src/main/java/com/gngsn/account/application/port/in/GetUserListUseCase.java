package com.gngsn.account.application.port.in;

import com.gngsn.account.application.domain.model.User;

import java.util.List;

public interface GetUserListUseCase {
    List<User> get();
}
