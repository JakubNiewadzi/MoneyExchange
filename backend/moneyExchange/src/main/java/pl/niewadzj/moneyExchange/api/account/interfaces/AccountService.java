package pl.niewadzj.moneyExchange.api.account.interfaces;

import pl.niewadzj.moneyExchange.api.account.records.AccountResponse;
import pl.niewadzj.moneyExchange.api.account.records.AccountUserInfoResponse;
import pl.niewadzj.moneyExchange.api.account.records.CurrencyAccountsPageResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

public interface AccountService {

    void createAccount(User owner);

    CurrencyAccountsPageResponse getCurrencyAccounts(int pageNo, int pageSize, User user);

    CurrencyAccountsPageResponse getActiveCurrencyAccounts(int pageNo, int pageSize, User user);

    AccountResponse getAccountByAccountNumber(String accountNumber);

    AccountUserInfoResponse getAccountForUser(User user);

    List<AccountResponse> getAccounts(int pageNo, int pageSize);

    List<CurrencyAccountResponse> getAllCurrencyAccountsForUser(User user);
}
