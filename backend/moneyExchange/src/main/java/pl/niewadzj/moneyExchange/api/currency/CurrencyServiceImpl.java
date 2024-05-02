package pl.niewadzj.moneyExchange.api.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currency.interfaces.CurrencyService;
import pl.niewadzj.moneyExchange.api.currency.mapper.CurrencyMapper;
import pl.niewadzj.moneyExchange.api.currency.records.CurrencyResponse;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.exceptions.currency.CurrencyNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;


    @Override
    public final List<CurrencyResponse> getCurrencies() {
        log.debug("Fetching all currencies from database");

        return currencyRepository
                .findAll()
                .stream().map(currencyMapper)
                .toList();
    }

    @Override
    public final CurrencyResponse getCurrency(Long id) {
        log.debug("Fetching currency with id: {}", id);

        Currency currency = currencyRepository
                .findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException(id));

        return currencyMapper
                .apply(currency);
    }

    @Override
    public List<CurrencyResponse> getExchangeRatesByCurrency(Long id) {
        BigDecimal exchangeRate = currencyRepository
                .findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException(id))
                .getExchangeRate();

        List<Currency> currencyResponses = currencyRepository
                .findAll();

        currencyResponses.forEach(currency -> currency.setExchangeRate(currency.getExchangeRate()
                .divide(exchangeRate, 6, RoundingMode.DOWN)));

        return currencyResponses.stream()
                .map(currencyMapper)
                .toList();
    }

}
