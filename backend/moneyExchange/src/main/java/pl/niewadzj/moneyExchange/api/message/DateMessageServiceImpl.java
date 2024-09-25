package pl.niewadzj.moneyExchange.api.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeService;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyRequest;
import pl.niewadzj.moneyExchange.api.message.interfaces.DateMessageService;
import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.DateMessagesResponse;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;
import pl.niewadzj.moneyExchange.entities.message.repositories.DateMessageRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.entities.user.interfaces.UserRepository;
import pl.niewadzj.moneyExchange.exceptions.auth.UserNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.message.DateMessageNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.message.MessageNotOwnedByUserException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateMessageServiceImpl implements DateMessageService {

    private final UserRepository userRepository;
    private final DateMessageRepository dateMessageRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final CurrencyExchangeService currencyExchangeService;

    @Override
    public void performDueTransactions() {
        Iterable<DateMessage> dateMessageIterable = dateMessageRepository.findAll();

        StreamSupport.stream(dateMessageIterable.spliterator(), false)
                .filter(dateMessage -> dateMessage.getTriggerDate().isBefore(LocalDateTime.now()))
                .forEach(dateMessage -> {
                    ExchangeCurrencyRequest exchangeCurrencyRequest = ExchangeCurrencyRequest.builder()
                            .currencyFromId(dateMessage.getSourceCurrencyId())
                            .currencyToId(dateMessage.getTargetCurrencyId())
                            .amount(dateMessage.getAmount())
                            .build();

                    Long userId = dateMessage.getUserId();

                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException(userId));

                    currencyExchangeService.exchangeCurrency(exchangeCurrencyRequest, user);
                    sendNotification(user, dateMessage.getMessage());
                });

        dateMessageRepository.deleteAll(dateMessageIterable);
    }

    @Override
    public void createDateMessage(DateMessageRequest dateMessageRequest,
                                  User user) {

        DateMessage dateMessage = DateMessage.builder()
                .id(null)
                .message(dateMessageRequest.message())
                .userId(user.getId())
                .sourceCurrencyId(dateMessageRequest.sourceCurrencyId())
                .targetCurrencyId(dateMessageRequest.targetCurrencyId())
                .amount(dateMessageRequest.amount())
                .triggerDate(dateMessageRequest.triggerDate())
                .build();

        dateMessageRepository.save(dateMessage);
    }

    @Override
    public DateMessagesResponse getDateMessageResponses(int pageNo, int pageSize, User user) {
        log.info("Finding date messages for a user");
        List<DateMessage> dateMessages = dateMessageRepository
                .findAll();

        dateMessages.forEach(System.out::println);

        dateMessages = dateMessages.stream()
                .filter(dateMessage -> Objects.equals(dateMessage.getUserId(), user.getId()))
                .toList();


        return DateMessagesResponse.builder()
                .dateMessages(dateMessages)
                .amount(dateMessages.size())
                .pages((dateMessages.size() / pageSize) + 1)
                .build();
    }

    @Override
    public void deleteDateMessage(Long id, User user) {
        DateMessage dateMessage = dateMessageRepository.findById(id)
                .orElseThrow(() -> new DateMessageNotFoundException(id));

        if (!Objects.equals(dateMessage.getUserId(), user.getId())){
            throw new MessageNotOwnedByUserException();
        }

        dateMessageRepository.deleteById(id);
    }

    @Override
    @MessageMapping("/private")
    public void sendNotification(User user, @Payload String message) {
        simpMessagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/notifications", message);
    }


}
