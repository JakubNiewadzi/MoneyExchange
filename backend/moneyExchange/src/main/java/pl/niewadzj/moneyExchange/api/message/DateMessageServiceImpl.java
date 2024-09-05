package pl.niewadzj.moneyExchange.api.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeService;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyRequest;
import pl.niewadzj.moneyExchange.api.message.interfaces.DateMessageService;
import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.entities.account.Account;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountRepository;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccount;
import pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces.CurrencyAccountRepository;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;
import pl.niewadzj.moneyExchange.entities.message.repositories.DateMessageRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.entities.user.interfaces.UserRepository;
import pl.niewadzj.moneyExchange.exceptions.account.AccountNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.auth.UserNotFoundException;

import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DateMessageServiceImpl implements DateMessageService {

    private final UserRepository userRepository;
    private final DateMessageRepository dateMessageRepository;
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

                    currencyExchangeService.exchangeCurrency(exchangeCurrencyRequest,
                            userRepository.findById(dateMessage.getUserId()).orElseThrow(() -> new UserNotFoundException("aa")));
                });

        dateMessageRepository.deleteAll(dateMessageIterable);
    }

    @Override
    public void createDateMessage(DateMessageRequest dateMessageRequest,
                                  User user) {

        DateMessage dateMessage = DateMessage.builder()
                .message(dateMessageRequest.message())
                .userId(user.getId())
                .sourceCurrencyId(dateMessageRequest.sourceCurrencyId())
                .targetCurrencyId(dateMessageRequest.targetCurrencyId())
                .amount(dateMessageRequest.amount())
                .triggerDate(dateMessageRequest.triggerDate())
                .build();

        dateMessageRepository.save(dateMessage);
    }

}
