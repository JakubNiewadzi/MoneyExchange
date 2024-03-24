package pl.niewadzj.moneyExchange.api.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.api.account.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.account.records.TransferRequest;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccountStatus;
import pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces.CurrencyAccountRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.exceptions.account.AccountNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.account.NotEnoughMoneyException;
import pl.niewadzj.moneyExchange.exceptions.currency.CurrencyNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.ACCOUNT_NUMBER_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyAccountRepository currencyAccountRepository;

    @Override
    public void createAccount(User owner) {
        log.debug("Creating account for user: {}", owner);
        List<Currency> currencies = currencyRepository.findAll();

        final Account account = Account.builder()
                .accountOwner(owner)
                .accountNumber(generateAccountNumber())
                .build();

        List<CurrencyAccount> currentAccountBalance = currencies.stream()
                .map(currency -> CurrencyAccount.builder()
                        .account(account)
                        .balance(BigDecimal.valueOf(0.00))
                        .currencyAccountStatus(CurrencyAccountStatus.INACTIVE)
                        .currency(currency)
                        .build())
                .collect(Collectors.toList());

        accountRepository.save(account);
        currencyAccountRepository.saveAllAndFlush(currentAccountBalance);
        log.debug("Account successfully created");
    }

    @Override
    public BalanceResponse depositToAccount(TransferRequest transferRequest, User user) {
        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user.getEmail()));

        Currency currencyToIncrease = currencyRepository.findById(transferRequest
                .currencyId()).orElseThrow(() -> new CurrencyNotFoundException(transferRequest.currencyId()));

        CurrencyAccount currencyBalance = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToIncrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), transferRequest.currencyId()));

        currencyBalance.setBalance(currencyBalance.getBalance().add(transferRequest.amount()));
        currencyBalance.setCurrencyAccountStatus(CurrencyAccountStatus.ACTIVE);

        currencyAccountRepository.saveAndFlush(currencyBalance);

        return BalanceResponse.builder()
                .currencyId(currencyToIncrease.getId())
                .currencyCode(currencyToIncrease.getCode())
                .balance(currencyBalance.getBalance())
                .build();
    }

    @Override
    public BalanceResponse withdrawFromAccount(TransferRequest transferRequest, User user) {
        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user.getEmail()));

        Currency currencyToIncrease = currencyRepository.findById(transferRequest.currencyId())
                .orElseThrow(() -> new CurrencyNotFoundException(transferRequest.currencyId()));

        CurrencyAccount currencyBalance = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToIncrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), transferRequest.currencyId()));

        currencyBalance.setBalance(currencyBalance.getBalance().subtract(transferRequest.amount()));

        if (currencyBalance.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughMoneyException();
        }

        currencyAccountRepository.saveAndFlush(currencyBalance);

        return BalanceResponse.builder()
                .currencyId(currencyToIncrease.getId())
                .currencyCode(currencyToIncrease.getCode())
                .balance(currencyBalance.getBalance())
                .build();
    }

    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumberBuilder = new StringBuilder();

        for (int i = 0; i < ACCOUNT_NUMBER_SIZE; i++) {
            int index = random.nextInt(0, 10);
            accountNumberBuilder.append(index);
        }

        return accountNumberBuilder.toString();
    }
}
