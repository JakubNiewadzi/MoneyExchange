package pl.niewadzj.moneyExchange.api.transfer.interfaces;

import pl.niewadzj.moneyExchange.api.transfer.records.TransferRequest;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface TransferService {

    TransferResponse makeTransfer(TransferRequest transferRequest, User user);

}
