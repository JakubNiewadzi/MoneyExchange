package pl.niewadzj.moneyExchange.api.transfer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.niewadzj.moneyExchange.api.transfer.interfaces.TransferService;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferRequest;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferResponse;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces.CurrencyAccountRepository;
import pl.niewadzj.moneyExchange.entities.transfer.Transfer;
import pl.niewadzj.moneyExchange.entities.transfer.TransferStatus;
import pl.niewadzj.moneyExchange.entities.transfer.interfaces.TransferRepository;
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
public class TransferServiceImpl implements TransferService {

    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final TransferRepository transferRepository;
    private final CurrencyAccountRepository currencyAccountRepository;

    @Override
    @Transactional
    public TransferResponse makeTransfer(TransferRequest transferRequest, User user) {
        log.debug("Performing transfer: {}", transferRequest);
        Account providerAccount = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user.getEmail()));

        Account receiverAccount = accountRepository.findByAccountNumber(transferRequest.receiverAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException(transferRequest.receiverAccountNumber()));

        Currency providerCurrency = currencyRepository.findById(transferRequest.providerCurrencyId())
                .orElseThrow(() -> new CurrencyNotFoundException(transferRequest.providerCurrencyId()));

        Currency receiverCurrency = currencyRepository.findById(transferRequest.receiverCurrencyId())
                .orElseThrow(() -> new CurrencyNotFoundException(transferRequest.receiverCurrencyId()));

        BigDecimal exchangeRate = receiverCurrency
                .getExchangeRate()
                .divide(providerCurrency.getExchangeRate(), 6, RoundingMode.DOWN);

        BigDecimal amountReceived = transferRequest
                .amountProvided()
                .divide(exchangeRate, 2, RoundingMode.DOWN);

        Transfer transfer = Transfer.builder()
                .transferDateTime(LocalDateTime.now())
                .providerAccount(providerAccount)
                .receiverAccount(receiverAccount)
                .providerCurrency(providerCurrency)
                .receiverCurrency(receiverCurrency)
                .exchangeRate(exchangeRate)
                .currencyProvided(transferRequest.amountProvided())
                .currencyReceived(amountReceived)
                .transferStatus(TransferStatus.PENDING)
                .build();

        transfer = transferRepository.saveAndFlush(transfer);

        decreaseCurrency(providerAccount, providerCurrency, transferRequest.amountProvided());
        increaseCurrency(receiverAccount, receiverCurrency, amountReceived);

        transfer.setTransferStatus(TransferStatus.SUCCESSFUL);
        transferRepository.saveAndFlush(transfer);

        return TransferResponse
                .builder()
                .currencyProvidedCode(providerCurrency.getCode())
                .amountProvided(transferRequest.amountProvided())
                .currencyReceivedCode(receiverCurrency.getCode())
                .amountReceived(amountReceived)
                .build();
    }

    private void decreaseCurrency(Account account, Currency currencyToDecrease, BigDecimal amount) {
        CurrencyAccount currencyAccount = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToDecrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyToDecrease.getId()));

        if (currencyAccount.getBalance()
                .subtract(amount)
                .compareTo(BigDecimal.ZERO) < 0.00) {

            throw new NotEnoughMoneyException();
        }
        currencyAccount.setBalance(currencyAccount
                .getBalance()
                .subtract(amount));

        currencyAccountRepository.saveAndFlush(currencyAccount);
    }

    private void increaseCurrency(Account account, Currency currencyToIncrease, BigDecimal amount) {
        CurrencyAccount currencyAccount = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToIncrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyToIncrease.getId()));

        currencyAccount.setBalance(currencyAccount
                .getBalance()
                .add(amount));

        currencyAccountRepository.saveAndFlush(currencyAccount);
    }
}
