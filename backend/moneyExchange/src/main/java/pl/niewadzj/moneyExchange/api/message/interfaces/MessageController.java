package pl.niewadzj.moneyExchange.api.message.interfaces;

import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.DateMessagesResponse;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessagesResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface MessageController {

    void createDateMessage(DateMessageRequest dateMessageRequest,
                           User user);

    void createValueMessage(ValueMessageRequest valueMessageRequest,
                            User user);

    DateMessagesResponse getDateMessages(int pageNo,
                                         int pageSize,
                                         User user);

    ValueMessagesResponse getValueMessages(int pageNo,
                                           int pageSize,
                                           User user);

}
