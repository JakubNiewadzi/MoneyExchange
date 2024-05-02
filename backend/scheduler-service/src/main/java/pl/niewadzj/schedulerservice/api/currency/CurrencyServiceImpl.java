package pl.niewadzj.schedulerservice.api.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.niewadzj.schedulerservice.api.currency.interfaces.CurrencyService;
import pl.niewadzj.schedulerservice.entities.currency.Currency;
import pl.niewadzj.schedulerservice.entities.currency.interfaces.CurrencyRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    @Override
    public void addExchangeRates(List<Currency> currencies) {
        log.debug("Posting currencies to database: {}", currencies);

        if (!currencyRepository.findAll().isEmpty()){
            return;
        }

        currencies.addFirst(Currency.builder()
                .name("Polski złoty")
                .code("PLN")
                .exchangeRate(BigDecimal.valueOf(1.0f))
                .rateDate(LocalDateTime.now())
                .build());

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

}
