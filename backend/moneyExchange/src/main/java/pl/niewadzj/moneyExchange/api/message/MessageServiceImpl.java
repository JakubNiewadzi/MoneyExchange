package pl.niewadzj.moneyExchange.api.message;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.message.interfaces.MessageService;

import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.MESSAGES_ENDPOINT;
import static pl.niewadzj.moneyExchange.websockets.constants.WebSocketsConstants.QUEUE;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendNotification(String username, String message) {
        simpMessagingTemplate.convertAndSend("%s/%s%s".formatted(QUEUE, username, MESSAGES_ENDPOINT),
                message);
    }
}
