package pl.niewadzj.moneyExchange.api.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.niewadzj.moneyExchange.api.transaction.interfaces.TransactionService;
import pl.niewadzj.moneyExchange.api.transaction.records.TransactionRequest;
import pl.niewadzj.moneyExchange.api.transaction.records.TransactionResponse;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccountStatus;
import pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces.CurrencyAccountRepository;
import pl.niewadzj.moneyExchange.entities.transaction.Transaction;
import pl.niewadzj.moneyExchange.entities.transaction.interfaces.TransactionRepository;
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
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;
    private final CurrencyAccountRepository currencyAccountRepository;

    @Override
    @Transactional
    public final TransactionResponse makeTransaction(TransactionRequest transactionRequest, User user) {
        log.debug("Performing transaction: {}", transactionRequest);
        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user.getEmail()));

        Currency currencyToDecrease = currencyRepository.findById(transactionRequest.currencyFromId())
                .orElseThrow(() -> new CurrencyNotFoundException(transactionRequest.currencyFromId()));

        Currency currencyToIncrease = currencyRepository.findById(transactionRequest.currencyToId())
                .orElseThrow(() -> new CurrencyNotFoundException(transactionRequest.currencyToId()));

        BigDecimal exchangeRate = currencyToIncrease
                .getExchangeRate()
                .divide(currencyToDecrease.getExchangeRate(), 6, RoundingMode.DOWN);

        BigDecimal decreasedCurrencyBalance = decreaseCurrency(account, currencyToDecrease, transactionRequest.amount());

        BigDecimal amountToIncrease = transactionRequest
                .amount()
                .divide(exchangeRate, 2, RoundingMode.DOWN);

        BigDecimal increasedCurrencyBalance = increaseCurrency(account, currencyToIncrease, amountToIncrease);

        Transaction transaction = Transaction.builder()
                .account(account)
                .exchangeRate(exchangeRate)
                .transactionDate(LocalDateTime.now())
                .exchangeFrom(currencyToDecrease)
                .exchangeTo(currencyToIncrease)
                .amountExchanged(transactionRequest.amount())
                .build();

        transactionRepository.saveAndFlush(transaction);

        return TransactionResponse.builder()
                .decreasedCurrencyId(transactionRequest.currencyFromId())
                .decreasedCurrencyCode(currencyToDecrease.getCode())
                .decreasedCurrencyBalance(decreasedCurrencyBalance)
                .increasedCurrencyId(transactionRequest.currencyToId())
                .increasedCurrencyCode(currencyToIncrease.getCode())
                .increasedCurrencyBalance(increasedCurrencyBalance)
                .transactionDate(transaction.getTransactionDate())
                .build();
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
