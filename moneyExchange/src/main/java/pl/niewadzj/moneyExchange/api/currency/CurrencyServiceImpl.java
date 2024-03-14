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

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;
    private final CurrencyMapper currencyMapper;

    @Override
    public void addExchangeRates(List<Currency> currencies) {
        log.debug("Posting currencies to database: {}", currencies);
        currencyRepository.saveAllAndFlush(currencies);
    }

    @Override
    public void updateExchangeRates(List<Currency> currencies) {
        log.debug("Updating currencies in database: {}", currencies);

        currencies.forEach(currency -> {
            log.debug("Updating currency: {}", currency);
            Optional<Currency> oldCurrencyOptional = currencyRepository.findByCode(currency.getCode());
            oldCurrencyOptional.ifPresent(oldCurrency -> {
                oldCurrency.setExchangeRate(currency.getExchangeRate());
                oldCurrency.setRateDate(currency.getRateDate());
                currencyRepository.saveAndFlush(oldCurrency);
            });
            if (oldCurrencyOptional.isEmpty()) {
                currencyRepository.saveAndFlush(currency);
            }
        });
    }

    @Override
    public List<CurrencyResponse> getCurrencies() {
        log.debug("Fetching all currencies from database");

        return currencyRepository
                .findAll()
                .stream().map(currencyMapper)
                .toList();
    }

    @Override
    public CurrencyResponse getCurrency(Long id) {
        log.debug("Fetching currency with id: {}", id);

        Currency currency = currencyRepository
                .findById(id)
                .orElseThrow(() -> new CurrencyNotFoundException(id));

        return currencyMapper
                .apply(currency);
    }

}
