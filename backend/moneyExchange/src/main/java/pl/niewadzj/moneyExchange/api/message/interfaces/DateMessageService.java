package pl.niewadzj.moneyExchange.api.message.interfaces;

import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface DateMessageService {

    void performDueTransactions();

    void createDateMessage(DateMessageRequest dateMessageRequest,
                           User user);

}
