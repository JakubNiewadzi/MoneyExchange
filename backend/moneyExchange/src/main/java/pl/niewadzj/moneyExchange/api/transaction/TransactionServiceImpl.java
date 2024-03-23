package pl.niewadzj.moneyExchange.api.transaction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.niewadzj.moneyExchange.api.transaction.interfaces.TransactionService;
import pl.niewadzj.moneyExchange.api.transaction.records.TransactionRequest;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.transaction.Transaction;
import pl.niewadzj.moneyExchange.entities.transaction.interfaces.TransactionRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.exceptions.account.AccountNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.account.NotEnoughMoneyException;
import pl.niewadzj.moneyExchange.exceptions.currency.CurrencyNotFoundException;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void makeTransaction(TransactionRequest transactionRequest, User user) {
        log.debug("Performing transaction: {}", transactionRequest);
        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user.getEmail()));

        Currency currencyToDecrease = currencyRepository.findById(transactionRequest.currencyFromId())
                .orElseThrow(() -> new CurrencyNotFoundException(transactionRequest.currencyFromId()));

        Currency currencyToIncrease = currencyRepository.findById(transactionRequest.currencyToId())
                .orElseThrow(() -> new CurrencyNotFoundException(transactionRequest.currencyToId()));

        Float exchangeRate = currencyToIncrease.getExchangeRate() / currencyToDecrease.getExchangeRate();
        //TODO refactor
        decreaseCurrency(account, currencyToDecrease, transactionRequest.amount());
        increaseCurrency(account, currencyToIncrease, (float) Math.floor((transactionRequest.amount() / exchangeRate) * 100.0f) / 100.0f);

        accountRepository.saveAndFlush(account);

        Transaction transaction = Transaction.builder()
                .account(account)
                .exchangeRate(exchangeRate)
                .transactionDate(LocalDateTime.now())
                .exchangeFrom(currencyToDecrease)
                .exchangeTo(currencyToIncrease)
                .amountExchanged(transactionRequest.amount())
                .build();

        transactionRepository.saveAndFlush(transaction);
    }

    private void decreaseCurrency(Account account, Currency currencyToDecrease, Float amount) {
        if (account.getAccountBalance().get(currencyToDecrease) - amount < 0.00) {
            throw new NotEnoughMoneyException();
        }

        account.getAccountBalance()
                .put(currencyToDecrease, account.getAccountBalance().get(currencyToDecrease) - amount);
    }

    private void increaseCurrency(Account account, Currency currencyToIncrease, Float amount) {
        account.getAccountBalance()
                .put(currencyToIncrease, account.getAccountBalance().get(currencyToIncrease) + amount);
    }
}
