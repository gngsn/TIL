package com.gngsn.account.adapter.in.web;

import com.gngsn.account.application.domain.model.User;
import com.gngsn.account.application.port.in.GetUserListUseCase;
import com.gngsn.account.common.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class UserController {

    private final GetUserListUseCase getUserListUseCase;

    @GetMapping(path = "/users")
    List<User> getUserList() {
        return getUserListUseCase.get();
    }
}
