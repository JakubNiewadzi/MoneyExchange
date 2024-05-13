package pl.niewadzj.schedulerservice.api.currency.mapper;

import org.springframework.stereotype.Service;
import pl.niewadzj.schedulerservice.api.currency.records.CurrencyResponse;
import pl.niewadzj.schedulerservice.entities.currency.Currency;

import java.util.function.Function;

@Service
public class CurrencyMapper implements Function<Currency, CurrencyResponse> {
    @Override
    public CurrencyResponse apply(Currency currency) {
        return CurrencyResponse.builder()
                .id(currency.getId())
                .code(currency.getCode())
                .name(currency.getName())
                .exchangeRate(currency.getExchangeRate())
                .rateDate(currency.getRateDate())
                .build();
    }
}
