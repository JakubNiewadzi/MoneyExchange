package pl.niewadzj.moneyExchange.api.transfer.interfaces;

import pl.niewadzj.moneyExchange.api.transfer.records.MakeTransferResponse;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferHistoryResponse;
import pl.niewadzj.moneyExchange.api.transfer.records.TransferRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface TransferController {

    MakeTransferResponse makeTransfer(TransferRequest transferRequest, User user);

    TransferHistoryResponse getTransfersForUser(int pageNo, int pageSize, User user);

    TransferHistoryResponse getTransfersForProviderUser(int pageNo, int pageSize, User user);

    TransferHistoryResponse getTransfersForReceiverUser(int pageNo, int pageSize, User user);

}
