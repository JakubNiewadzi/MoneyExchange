package pl.niewadzj.moneyExchange.api.currencyExchange;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeService;
import pl.niewadzj.moneyExchange.api.currencyExchange.mapper.CurrencyExchangeMapper;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyExchangeResponse;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyRequest;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyResponse;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccountStatus;
import pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces.CurrencyAccountRepository;
import pl.niewadzj.moneyExchange.entities.currencyExchange.CurrencyExchange;
import pl.niewadzj.moneyExchange.entities.currencyExchange.CurrencyExchangeStatus;
import pl.niewadzj.moneyExchange.entities.currencyExchange.interfaces.CurrencyExchangeRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.exceptions.account.AccountNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.account.NotEnoughMoneyException;
import pl.niewadzj.moneyExchange.exceptions.currency.CurrencyNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyExchangeMapper currencyExchangeMapper;
    private final CurrencyAccountRepository currencyAccountRepository;
    private final CurrencyExchangeRepository currencyExchangeRepository;

    @Override
    @Transactional
    public ExchangeCurrencyResponse exchangeCurrency(ExchangeCurrencyRequest exchangeCurrencyRequest, User user) {
        log.debug("Performing transaction: {}", exchangeCurrencyRequest);
        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user.getEmail()));

        Currency currencyToDecrease = currencyRepository.findById(exchangeCurrencyRequest.currencyFromId())
                .orElseThrow(() -> new CurrencyNotFoundException(exchangeCurrencyRequest.currencyFromId()));

        Currency currencyToIncrease = currencyRepository.findById(exchangeCurrencyRequest.currencyToId())
                .orElseThrow(() -> new CurrencyNotFoundException(exchangeCurrencyRequest.currencyToId()));

        BigDecimal exchangeRate = currencyToIncrease
                .getExchangeRate()
                .divide(currencyToDecrease.getExchangeRate(), 6, RoundingMode.DOWN);

        BigDecimal decreasedCurrencyBalance = decreaseCurrency(account, currencyToDecrease, exchangeCurrencyRequest.amount());

        BigDecimal amountToIncrease = exchangeCurrencyRequest
                .amount()
                .divide(exchangeRate, 2, RoundingMode.DOWN);

        BigDecimal increasedCurrencyBalance = increaseCurrency(account, currencyToIncrease, amountToIncrease);

        CurrencyExchange currencyExchange = CurrencyExchange.builder()
                .account(account)
                .exchangeRate(exchangeRate)
                .exchangeDateTime(LocalDateTime.now())
                .decreasedCurrency(currencyToDecrease)
                .increasedCurrency(currencyToIncrease)
                .amountDecreased(exchangeCurrencyRequest.amount())
                .amountIncreased(amountToIncrease)
                .currencyExchangeStatus(CurrencyExchangeStatus.SUCCESSFUL)
                .build();

        currencyExchangeRepository.saveAndFlush(currencyExchange);

        return ExchangeCurrencyResponse.builder()
                .decreasedCurrencyId(exchangeCurrencyRequest.currencyFromId())
                .decreasedCurrencyCode(currencyToDecrease.getCode())
                .decreasedCurrencyBalance(decreasedCurrencyBalance)
                .increasedCurrencyId(exchangeCurrencyRequest.currencyToId())
                .increasedCurrencyCode(currencyToIncrease.getCode())
                .increasedCurrencyBalance(increasedCurrencyBalance)
                .transactionDateTime(currencyExchange.getExchangeDateTime())
                .build();
    }

    @Override
    public Page<CurrencyExchangeResponse> getExchangesHistoryForUser(int pageNo, int pageSize, User user) {
        log.debug("Getting exchange history for user: {}", user);

        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user.getEmail()));

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        return currencyExchangeRepository.findByAccountAndCurrencyExchangeStatus(account,
                CurrencyExchangeStatus.SUCCESSFUL,
                pageable).map(currencyExchangeMapper);
    }

    private BigDecimal decreaseCurrency(Account account, Currency currencyToDecrease, BigDecimal amount) {
        CurrencyAccount currencyAccount = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToDecrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyToDecrease.getId()));

        if (currencyAccount
                .getBalance()
                .subtract(amount)
                .compareTo(BigDecimal.ZERO) < 0.00) {

            throw new NotEnoughMoneyException();
        }
        currencyAccount
                .setBalance(currencyAccount
                        .getBalance()
                        .subtract(amount));

        currencyAccountRepository.saveAndFlush(currencyAccount);

        return currencyAccount.getBalance();
    }

    private BigDecimal increaseCurrency(Account account, Currency currencyToIncrease, BigDecimal amount) {
        CurrencyAccount currencyAccount = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToIncrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyToIncrease.getId()));

        currencyAccount.setCurrencyAccountStatus(CurrencyAccountStatus.ACTIVE);

        currencyAccount
                .setBalance(currencyAccount
                        .getBalance()
                        .add(amount));

        currencyAccountRepository.saveAndFlush(currencyAccount);

        return currencyAccount.getBalance();
    }
}
