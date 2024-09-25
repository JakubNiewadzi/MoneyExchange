package pl.niewadzj.moneyExchange.api.message.interfaces;

import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.DateMessagesResponse;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface DateMessageService {

    void performDueTransactions();

    void createDateMessage(DateMessageRequest dateMessageRequest,
                           User user);

    DateMessagesResponse getDateMessageResponses(int pageNo, int pageSize, User user);

    void deleteDateMessage(Long id, User user);

    void sendNotification(User user, String message);
}
