package pl.niewadzj.moneyExchange.api.account.interfaces;

import pl.niewadzj.moneyExchange.api.account.records.AccountResponse;
import pl.niewadzj.moneyExchange.api.account.records.AccountUserInfoResponse;
import pl.niewadzj.moneyExchange.api.account.records.CurrencyAccountsPageResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

public interface AccountController {

    CurrencyAccountsPageResponse getCurrencyAccounts(int pageNo, int pageSize, User user);

    CurrencyAccountsPageResponse getActiveCurrencyAccounts(int pageNo, int pageSize, User user);

    List<CurrencyAccountResponse> getAllCurrencyAccountsForUser(User user);

    AccountResponse getAccountByAccountNumber(String accountNumber, User user);

    AccountUserInfoResponse getAccountForUser(User user);

    List<AccountResponse> getAllAccounts(int pageNo, int pageSize);
}
