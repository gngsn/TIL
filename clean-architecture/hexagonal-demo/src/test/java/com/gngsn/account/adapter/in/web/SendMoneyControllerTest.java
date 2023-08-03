package com.gngsn.account.adapter.in.web;

import com.gngsn.account.application.domain.model.Account.AccountId;
import com.gngsn.account.application.domain.model.Money;
import com.gngsn.account.application.domain.service.SendMoneyService;
import com.gngsn.account.application.port.in.SendMoneyCommand;
import com.gngsn.account.application.port.in.SendMoneyUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {SendMoneyController.class})
class SendMoneyControllerTest {

    @Mock
    private MockMvc mockMvc;

//    @MockBean
//    private SendMoneyUseCase sendMoneyUseCase;

    @MockBean
    private SendMoneyService sendMoneyService;

    @Test
    public void testSendMoney() throws Exception {
        mockMvc.perform(post("/accounts/send/{sourceAccountId}/{targetAccountId}/{amount}",
                        41L, 42L, 500)
                        .header("Content-Type", "application/json"))
                .andExpect(status().isOk());

        then(sendMoneyService).should()
                .sendMoney(eq(new SendMoneyCommand(
                        new AccountId(41L),
                        new AccountId(42L),
                        Money.of(500L))));
    }
}