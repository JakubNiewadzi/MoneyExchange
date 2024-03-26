package pl.niewadzj.moneyExchange.api.transaction.interfaces;

import pl.niewadzj.moneyExchange.api.transaction.records.TransactionRequest;
import pl.niewadzj.moneyExchange.api.transaction.records.TransactionResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface TransactionService {
    TransactionResponse makeTransaction(TransactionRequest transactionRequest, User user);
}
