package pl.niewadzj.moneyExchange.api.transaction.interfaces;

import pl.niewadzj.moneyExchange.api.transaction.records.TransactionRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface TransactionController {

    void makeTransaction(TransactionRequest transactionRequest, User user);
}
