package pl.niewadzj.moneyExchange.api.transfer.interfaces;

import pl.niewadzj.moneyExchange.api.transfer.records.TransferRequest;
import pl.niewadzj.moneyExchange.api.transfer.records.MakeTransferResponse;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import java.util.List;

public interface TransferService {

    MakeTransferResponse makeTransfer(TransferRequest transferRequest, User user);

    List<TransferResponse> getTransfersForUser(int pageNo, int pageSize, User user);

    List<TransferResponse> getTransfersForReceiverUser(int pageNo, int pageSize, User user);

    List<TransferResponse> getTransfersForProviderUser(int pageNo, int pageSize, User user);
}
