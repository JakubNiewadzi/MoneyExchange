package pl.niewadzj.moneyExchange.api.message.interfaces;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.DateMessagesResponse;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessagesResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.DELETE_DATE_MESSAGE;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.DELETE_VALUE_MESSAGE;

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

    void deleteValueMessage(Long id, User user);

    void deleteDateMessage(Long id, User user);
}
