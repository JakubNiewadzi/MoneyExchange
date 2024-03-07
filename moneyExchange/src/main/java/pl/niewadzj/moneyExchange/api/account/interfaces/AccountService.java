package pl.niewadzj.moneyExchange.api.account.interfaces;

import pl.niewadzj.moneyExchange.api.account.records.BalanceResponse;
import pl.niewadzj.moneyExchange.api.account.records.TopUpRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface AccountService {

    void createAccount(User owner);

    BalanceResponse topUpAccount(TopUpRequest topUpRequest, User user);
}
