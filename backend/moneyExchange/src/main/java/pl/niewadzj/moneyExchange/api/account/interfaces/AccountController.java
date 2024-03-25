package pl.niewadzj.moneyExchange.api.account.interfaces;

import pl.niewadzj.moneyExchange.api.account.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.account.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.api.account.records.TransferRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

public interface AccountController {

    BalanceResponse depositToAccount(TransferRequest transferRequest,
                                     User user);

    BalanceResponse withdrawFromAccount(TransferRequest transferRequest,
                                        User user);

    List<CurrencyAccountResponse> getCurrencyAccounts(User user);
    CurrencyAccountResponse getCurrencyAccountByCurrencyId(Long currencyId, User user);
    List<CurrencyAccountResponse> getActiveCurrencyAccounts(User user);
}
