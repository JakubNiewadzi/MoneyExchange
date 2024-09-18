package pl.niewadzj.moneyExchange.api.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currencyExchange.interfaces.CurrencyExchangeService;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyRequest;
import pl.niewadzj.moneyExchange.api.message.interfaces.ValueMessageService;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageCurrencies;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessagesResponse;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.message.ValueMessage;
import pl.niewadzj.moneyExchange.entities.message.repositories.ValueMessageRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.entities.user.interfaces.UserRepository;
import pl.niewadzj.moneyExchange.exceptions.auth.UserNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.currency.CurrencyNotFoundException;
import pl.niewadzj.moneyExchange.exceptions.message.InvalidMessageValue;
import pl.niewadzj.moneyExchange.exceptions.message.MessageNotOwnedByUserException;
import pl.niewadzj.moneyExchange.exceptions.message.ValueMessageNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ValueMessageServiceImpl implements ValueMessageService {

    private final UserRepository userRepository;
    private final CurrencyRepository currencyRepository;
    private final ValueMessageRepository valueMessageRepository;
    private final CurrencyExchangeService currencyExchangeService;

    @Override
    public void performValueCheckOnMessages() {
        Iterable<ValueMessage> valueMessages = valueMessageRepository.findAll();

        Map<ValueMessageCurrencies, BigDecimal> cachedRates = new HashMap<>();

        valueMessages.forEach(valueMessage -> {
            Long sourceCurrencyId = valueMessage.getSourceCurrencyId();
            Long targetCurrencyId = valueMessage.getTargetCurrencyId();

            Currency sourceCurrency = currencyRepository.findById(sourceCurrencyId)
                    .orElseThrow(() -> new CurrencyNotFoundException(sourceCurrencyId));

            Currency targetCurrency = currencyRepository.findById(targetCurrencyId)
                    .orElseThrow(() -> new CurrencyNotFoundException(targetCurrencyId));

            ValueMessageCurrencies currencies = ValueMessageCurrencies.builder()
                    .sourceCurrencyId(sourceCurrencyId)
                    .targetCurrencyId(targetCurrencyId)
                    .build();

            BigDecimal exchangeRate;

            if (cachedRates.containsKey(currencies)) {
                exchangeRate = cachedRates.get(currencies);
            } else {
                exchangeRate = targetCurrency.getExchangeRate()
                        .divide(sourceCurrency.getExchangeRate(), RoundingMode.HALF_DOWN);
            }

            cachedRates.putIfAbsent(currencies, exchangeRate);

            performExchange(exchangeRate, valueMessage);
        });
    }

    @Override
    public void createValueMessage(ValueMessageRequest valueMessageRequest, User user) {
        validateValueMessage(valueMessageRequest);

        ValueMessage valueMessage = ValueMessage.builder()
                .message(valueMessageRequest.message())
                .userId(user.getId())
                .sourceCurrencyId(valueMessageRequest.sourceCurrencyId())
                .targetCurrencyId(valueMessageRequest.targetCurrencyId())
                .amount(valueMessageRequest.amount())
                .value(valueMessageRequest.valueExchangeRate())
                .build();

        valueMessageRepository.save(valueMessage);
    }

    @Override
    public ValueMessagesResponse getValueMessageResponses(int pageNo, int pageSize, User user) {
        List<ValueMessage> valueMessages = valueMessageRepository
                .findAll();

        valueMessages.forEach(System.out::println);

        valueMessages = valueMessages.stream()
                .filter(valueMessage -> Objects.equals(valueMessage.getUserId(), user.getId()))
                .toList();


        return ValueMessagesResponse.builder()
                .valueMessages(valueMessages)
                .amount(valueMessages.size())
                .pages((valueMessages.size() / pageSize) + 1)
                .build();
    }

    @Override
    public void deleteValueMessage(Long id, User user) {
        ValueMessage valueMessage = valueMessageRepository.findById(id)
                .orElseThrow(() -> new ValueMessageNotFoundException(id));

        if (!Objects.equals(valueMessage.getUserId(), user.getId())) {
            throw new MessageNotOwnedByUserException();
        }

        valueMessageRepository.deleteById(id);
    }

    private void validateValueMessage(ValueMessageRequest valueMessageRequest) {
        Long sourceCurrencyId = valueMessageRequest.sourceCurrencyId();
        Long targetCurrencyId = valueMessageRequest.targetCurrencyId();

        BigDecimal sourceCurrencyExchangeRate = currencyRepository.findById(sourceCurrencyId)
                .orElseThrow(() -> new CurrencyNotFoundException(sourceCurrencyId))
                .getExchangeRate();

        BigDecimal targetCurrencyExchangeRate = currencyRepository.findById(targetCurrencyId)
                .orElseThrow(() -> new CurrencyNotFoundException(targetCurrencyId))
                .getExchangeRate();

        double currentExchangeRate = targetCurrencyExchangeRate
                .divide(sourceCurrencyExchangeRate, RoundingMode.HALF_DOWN)
                .doubleValue();

        double valueExchangeRate = valueMessageRequest.valueExchangeRate()
                .doubleValue();

        if (valueExchangeRate < 0.0
                || currentExchangeRate - valueExchangeRate > 1.0
                || valueExchangeRate > currentExchangeRate) {
            throw new InvalidMessageValue();
        }

    }

    private void performExchange(BigDecimal exchangeRate,
                                 ValueMessage valueMessage) {
        if (exchangeRate.doubleValue() < valueMessage.getValue().doubleValue()) {
            ExchangeCurrencyRequest exchangeCurrencyRequest = ExchangeCurrencyRequest.builder()
                    .currencyFromId(valueMessage.getSourceCurrencyId())
                    .currencyToId(valueMessage.getTargetCurrencyId())
                    .amount(valueMessage.getAmount())
                    .build();

            Long userId = valueMessage.getUserId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));

            currencyExchangeService.exchangeCurrency(exchangeCurrencyRequest, user);
            valueMessageRepository.delete(valueMessage);
        }
    }
}
