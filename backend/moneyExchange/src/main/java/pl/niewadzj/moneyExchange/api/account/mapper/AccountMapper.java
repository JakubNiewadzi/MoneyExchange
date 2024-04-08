package pl.niewadzj.moneyExchange.api.account.mapper;

import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.account.records.AccountResponse;
import pl.niewadzj.moneyExchange.entities.account.Account;

import java.util.function.Function;

@Service
public class AccountMapper implements Function<Account, AccountResponse> {
    @Override
    public AccountResponse apply(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .build();
    }
}
