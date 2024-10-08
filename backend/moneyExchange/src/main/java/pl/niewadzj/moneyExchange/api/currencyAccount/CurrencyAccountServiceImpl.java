package pl.niewadzj.moneyExchange.api.currencyAccount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currencyAccount.interfaces.CurrencyAccountService;
import pl.niewadzj.moneyExchange.api.currencyAccount.mapper.CurrencyAccountMapper;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.TransactionRequest;
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
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountAlreadyActive;
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountNotActiveException;
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountSuspendedException;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyAccountServiceImpl implements CurrencyAccountService {

    private final AccountRepository accountRepository;
    private final CurrencyRepository currencyRepository;
    private final CurrencyAccountMapper currencyAccountMapper;
    private final CurrencyAccountRepository currencyAccountRepository;

    @Override
    public final BalanceResponse depositToAccount(TransactionRequest transactionRequest, User user) {
        log.debug("Depositing onto account for user {}", user);
        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        Currency currencyToIncrease = currencyRepository.findById(transactionRequest
                .currencyId()).orElseThrow(() -> new CurrencyNotFoundException(transactionRequest.currencyId()));

        CurrencyAccount currencyBalance = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToIncrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), transactionRequest.currencyId()));

        if (currencyBalance.getCurrencyAccountStatus() == CurrencyAccountStatus.SUSPENDED) {
            throw new CurrencyAccountSuspendedException();
        }

        currencyBalance.setBalance(currencyBalance.getBalance().add(transactionRequest.amount()));
        currencyBalance.setCurrencyAccountStatus(CurrencyAccountStatus.ACTIVE);

        currencyAccountRepository.saveAndFlush(currencyBalance);

        return BalanceResponse.builder()
                .currencyId(currencyToIncrease.getId())
                .currencyCode(currencyToIncrease.getCode())
                .balance(currencyBalance.getBalance())
                .build();
    }

    @Override
    public final BalanceResponse withdrawFromAccount(TransactionRequest transactionRequest, User user) {
        log.debug("Withdrawing from account for user {}", user);
        Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        Currency currencyToIncrease = currencyRepository.findById(transactionRequest.currencyId())
                .orElseThrow(() -> new CurrencyNotFoundException(transactionRequest.currencyId()));

        CurrencyAccount currencyBalance = currencyAccountRepository
                .findByCurrencyAndAccount(currencyToIncrease, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), transactionRequest.currencyId()));

        if (currencyBalance.getCurrencyAccountStatus() == CurrencyAccountStatus.SUSPENDED) {
            throw new CurrencyAccountSuspendedException();
        }

        currencyBalance.setBalance(currencyBalance.getBalance().subtract(transactionRequest.amount()));

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

    @Override
    public final CurrencyAccountResponse getCurrencyAccountByCurrencyId(Long currencyId, User user) {
        log.debug("Fetching currency account for currency with id {}", currencyId);
        final Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        final Currency currency = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyId));

        return currencyAccountMapper
                .apply(currencyAccountRepository.findByCurrencyAndAccount(currency, account)
                        .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyId)));
    }

    @Override
    public final void suspendCurrencyAccount(Long currencyId, User user) {
        final Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        final Currency currency = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new CurrencyNotFoundException(currencyId));

        CurrencyAccount currencyAccount = currencyAccountRepository
                .findByCurrencyAndAccount(currency, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyId));

        if (currencyAccount.getCurrencyAccountStatus() != CurrencyAccountStatus.ACTIVE) {
            throw new CurrencyAccountNotActiveException();
        }

        currencyAccount.setCurrencyAccountStatus(CurrencyAccountStatus.SUSPENDED);
        currencyAccountRepository.saveAndFlush(currencyAccount);
    }

    @Override
    public final void activateCurrencyAccount(Long currencyId, User user) {
        final Account account = accountRepository.findByAccountOwner(user)
                .orElseThrow(() -> new AccountNotFoundException(user));

        final Currency currency = currencyRepository.findById(currencyId)
                .orElseThrow(() -> new CurrencyNotFoundException(currencyId));

        CurrencyAccount currencyAccount = currencyAccountRepository.findByCurrencyAndAccount(currency, account)
                .orElseThrow(() -> new CurrencyAccountNotFoundException(account.getId(), currencyId));

        if (currencyAccount.getCurrencyAccountStatus() == CurrencyAccountStatus.ACTIVE) {
            throw new CurrencyAccountAlreadyActive();
        }

        currencyAccount.setCurrencyAccountStatus(CurrencyAccountStatus.ACTIVE);
        currencyAccountRepository.saveAndFlush(currencyAccount);
    }

}
