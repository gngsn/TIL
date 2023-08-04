package com.gngsn.account.adapter.out.client;

import com.gngsn.account.application.domain.model.User;

import java.util.List;

public record GetUsersResponse(
        List<Result> results
) {

    public record Result(
            String gender,
            String email
    ) {
    }

    public List<User> toUserList() {
        return this.results.stream().map(result -> new User(result.gender, result.email)).toList();
    }
}
