package com.gngsn.account.adapter.in.web;

import com.gngsn.account.application.domain.model.User;
import com.gngsn.account.application.port.in.GetUserListUseCase;
import com.gngsn.account.common.WebAdapter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
public class UserController {

    private final GetUserListUseCase getUserListUseCase;

    public UserController(final GetUserListUseCase getUserListUseCase) {
        this.getUserListUseCase = getUserListUseCase;
    }

    @PostMapping(path = "/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}")
    List<User> sendMoney() {
        return getUserListUseCase.get();
    }
}
