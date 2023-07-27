package com.gngsn.account.application.port.in;

import com.gngsn.account.domain.Account;
import com.gngsn.account.domain.Money;
import com.gngsn.shared.SelfValidating;
import jakarta.validation.constraints.NotNull;

import static java.util.Objects.requireNonNull;

public class SendMoneyCommand extends SelfValidating<SendMoneyCommand> {

    @NotNull
    private final Account.AccountId sourceAccountId;
    @NotNull
    private final Account.AccountId targetAccountId;
    @NotNull
    private final Money money;

    /**
     * 입력 유효성 체크하는 생성자
     * - 애플리케이션 계층에서 입력 유효성을 검증하는 이유: 애플리케이션 바깥에서 유효하지 않은 입력값을 받게 되고, 모델의 상태를 해칠 수 있기 때문.
     * - '입력 유효성 체크'는 유스케이스의 역할이 아님 → 입력 모델인 SendMoneyCommand 가 적합.
     */
    public SendMoneyCommand(final Account.AccountId sourceAccountId,
                            final Account.AccountId targetAccountId,
                            final Money money) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
        requireNonNull(sourceAccountId);
        requireNonNull(targetAccountId);
        // requireGreaterThan(money, 0);
        this.validateSelf();
    }

    public Account.AccountId getSourceAccountId() {
        return sourceAccountId;
    }

    public Account.AccountId getTargetAccountId() {
        return targetAccountId;
    }

    public Money getMoney() {
        return money;
    }
}
