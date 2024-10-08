package pl.niewadzj.moneyExchange.api.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.api.account.mapper.AccountMapper;
import pl.niewadzj.moneyExchange.api.account.records.AccountResponse;
import pl.niewadzj.moneyExchange.api.account.records.AccountUserInfoResponse;
import pl.niewadzj.moneyExchange.api.account.records.CurrencyAccountsPageResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.mapper.CurrencyAccountMapper;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccountStatus;
import pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces.CurrencyAccountRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.exceptions.account.AccountNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static pl.niewadzj.moneyExchange.entities.account.constants.AccountConstants.ACCOUNT_NUMBER_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyAccountMapper currencyAccountMapper;
    private final CurrencyAccountRepository currencyAccountRepository;
    private final Random random = new Random();

    @Override
    public final void createAccount(User owner) {
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
    public final CurrencyAccountsPageResponse getCurrencyAccounts(int pageNo, int pageSize, User user) {
        log.debug("Fetching all currency accounts for user {}", user);
        final Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("balance")
                .descending()
                .and(Sort.by("currencyAccountStatus")));

        Page<CurrencyAccount> currencyAccountResponsePage = currencyAccountRepository
                .findByAccount(account, pageable);

        return CurrencyAccountsPageResponse.builder()
                .currencyAccountResponses(currencyAccountResponsePage.getContent()
                        .stream()
                        .map(currencyAccountMapper)
                        .toList())
                .amount(currencyAccountResponsePage.getTotalElements())
                .pages(currencyAccountResponsePage.getTotalPages())
                .build();

    }

    @Override
    public final CurrencyAccountsPageResponse getActiveCurrencyAccounts(int pageNo, int pageSize, User user) {
        log.debug("Fetching all currency accounts for user {}", user);
        final Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("balance").descending());

        Page<CurrencyAccount> currencyAccountResponsePage = currencyAccountRepository
                .findByAccountAndCurrencyAccountStatus(account, CurrencyAccountStatus.ACTIVE, pageable);

        return CurrencyAccountsPageResponse.builder()
                .currencyAccountResponses(currencyAccountResponsePage.getContent()
                        .stream()
                        .map(currencyAccountMapper)
                        .toList())
                .amount(currencyAccountResponsePage.getTotalElements())
                .pages(currencyAccountResponsePage.getTotalPages())
                .build();
    }

    @Override
    public final AccountResponse getAccountByAccountNumber(String accountNumber) {
        final Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        return accountMapper.apply(account);
    }

    @Override
    public final AccountUserInfoResponse getAccountForUser(User user) {
        final Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        return AccountUserInfoResponse.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getFirstName())
                .build();
    }

    @Override
    public final List<AccountResponse> getAccounts(int pageNo, int pageSize) {
        log.debug("Getting page with accounts");
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return accountRepository.findAll(pageable)
                .map(accountMapper)
                .getContent();
    }

    @Override
    public final List<CurrencyAccountResponse> getAllCurrencyAccountsForUser(User user) {
        log.debug("Getting all currency accounts for user");

        final Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        return account.getAccountBalance()
                .stream()
                .map(currencyAccountMapper)
                .toList();
    }

    private String generateAccountNumber() {
        StringBuilder accountNumberBuilder = new StringBuilder();

        for (int i = 0; i < ACCOUNT_NUMBER_SIZE; i++) {
            int index = random.nextInt(0, 10);
            accountNumberBuilder.append(index);
        }

        return accountNumberBuilder.toString();
    }
}
