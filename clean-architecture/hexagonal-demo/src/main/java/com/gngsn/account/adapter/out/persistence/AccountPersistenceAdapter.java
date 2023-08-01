package com.gngsn.account.adapter.out.persistence;

import com.gngsn.account.application.port.out.LoadAccountPort;
import com.gngsn.account.application.port.out.UpdateAccountStatePort;
import com.gngsn.account.application.domain.model.Account;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public record AccountPersistenceAdapter(
        AccountRepository accountRepository,
        ActivityRepository activityRepository,
        AccountMapper accountMapper
) implements LoadAccountPort, UpdateAccountStatePort {


    public Account loadaccount(
            Account.AccountId accountId,
            LocalDateTime baselineDate) {

        AccountJpaEntity account = accountRepository.findById(accountId.value())
                .orElseThrow(EntityNotFoundException::new);

        List<ActivityJpaEntity> activities =
                activityRepository.findByOwnerSince(
                        accountId.getValue(),
                        baselineDate);

        Long withdrawalBalance = activityRepository
                .getWithdrawalBalanceUntil(
                        accountId.getValue(),
                        baselineDate)
                .orElse(0L);

        Long depositBalance = activityRepository
                .getDepositBalanceUntil(
                        accountId.getValue(),
                        baselineDate)
                .orElse(0L);

        return accountMapper.mapToDomainEntity(
                account,
                activities,
                withdrawalBalance,
                depositBalance);
    }
}
