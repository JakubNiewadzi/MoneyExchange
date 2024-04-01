package pl.niewadzj.moneyExchange.api.currencyAccount.interfaces;

import pl.niewadzj.moneyExchange.api.account.records.TransferRequest;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface CurrencyAccountController {

    BalanceResponse depositToAccount(TransferRequest transferRequest,
                                     User user);

    BalanceResponse withdrawFromAccount(TransferRequest transferRequest,
                                        User user);

    CurrencyAccountResponse getCurrencyAccountByCurrencyId(Long currencyId, User user);

    void suspendCurrencyAccount(Long currencyId, User user);

    void activateSuspendedCurrencyAccount(Long currencyId, User user);


}
