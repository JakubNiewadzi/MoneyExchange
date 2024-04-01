package pl.niewadzj.moneyExchange.api.currencyAccount.interfaces;

import pl.niewadzj.moneyExchange.api.currencyAccount.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.TransactionRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface CurrencyAccountController {

    BalanceResponse depositToAccount(TransactionRequest transactionRequest,
                                     User user);

    BalanceResponse withdrawFromAccount(TransactionRequest transactionRequest,
                                        User user);

    CurrencyAccountResponse getCurrencyAccountByCurrencyId(Long currencyId, User user);

    void suspendCurrencyAccount(Long currencyId, User user);

    void activateSuspendedCurrencyAccount(Long currencyId, User user);


}
