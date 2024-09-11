package pl.niewadzj.moneyExchange.api.message.interfaces;

import pl.niewadzj.moneyExchange.api.message.records.DateMessagesResponse;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessagesResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface ValueMessageService {

    void performValueCheckOnMessages();

    void createValueMessage(ValueMessageRequest valueMessageRequest,
                            User user);

    ValueMessagesResponse getValueMessageResponses(int pageNo, int pageSize, User user);

    void deleteValueMessage(Long id, User user);
}
