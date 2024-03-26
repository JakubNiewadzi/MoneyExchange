package pl.niewadzj.moneyExchange.api.currency.mapper;

import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currency.records.CurrencyResponse;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.util.function.Function;

@Service
public class CurrencyMapper implements Function<Currency, CurrencyResponse> {
    @Override
    public CurrencyResponse apply(Currency currency) {
        return new CurrencyResponse(currency.getId(),
                currency.getName(),
                currency.getCode(),
                currency.getRateDate(),
                currency.getExchangeRate());
    }
}
