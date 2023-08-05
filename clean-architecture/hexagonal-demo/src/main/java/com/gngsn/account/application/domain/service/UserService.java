package com.gngsn.account.application.domain.service;

import com.gngsn.account.adapter.out.client.UserClient;
import com.gngsn.account.application.domain.model.User;
import com.gngsn.account.application.port.in.GetUserListUseCase;
import com.gngsn.account.common.UseCase;
import jakarta.transaction.Transactional;

import java.util.List;

@UseCase
public class UserService implements GetUserListUseCase {
    private final UserClient userClient;

    public UserService(final UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public List<User> get() {
        return userClient.getUsers("us").toUserList();
    }
}
