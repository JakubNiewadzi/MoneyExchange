package pl.niewadzj.moneyExchange.api.account.interfaces;

import pl.niewadzj.moneyExchange.api.account.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.account.records.TransferRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface AccountService {

    void createAccount(User owner);

    BalanceResponse depositToAccount(TransferRequest transferRequest, User user);

    BalanceResponse withdrawFromAccount(TransferRequest transferRequest, User user);
}
