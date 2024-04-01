package pl.niewadzj.moneyExchange.api.account.interfaces;

import pl.niewadzj.moneyExchange.api.account.records.AccountResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

public interface AccountController {

    List<CurrencyAccountResponse> getCurrencyAccounts(User user);

    List<CurrencyAccountResponse> getActiveCurrencyAccounts(User user);

    AccountResponse getAccountByAccountNumber(String accountNumber, User user);
}
