package pl.niewadzj.moneyExchange.entities.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;

    @Override
    public void createAccount(User owner) {
        log.debug("Creating account for user: {}", owner);
        List<Currency> currencies = currencyRepository.findAll();

        Map<Currency, Float> currentAccountBalance = new HashMap<>();

        currencies.forEach(currency -> currentAccountBalance.put(currency, 0.00f));

        Account account = Account.builder()
                .accountOwner(owner)
                .accountBalance(currentAccountBalance)
                .build();

        accountRepository.save(account);
        log.debug("Account successfully created");
    }
}
