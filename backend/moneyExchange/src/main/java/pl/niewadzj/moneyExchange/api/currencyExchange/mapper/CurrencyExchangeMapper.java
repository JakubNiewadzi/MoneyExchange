package pl.niewadzj.moneyExchange.api.currencyExchange.mapper;

import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyExchangeResponse;
import pl.niewadzj.moneyExchange.entities.currencyExchange.CurrencyExchange;

import java.util.function.Function;

@Service
public class CurrencyExchangeMapper implements Function<CurrencyExchange, CurrencyExchangeResponse> {
    @Override
    public CurrencyExchangeResponse apply(CurrencyExchange currencyExchange) {
        return CurrencyExchangeResponse.builder()
                .id(currencyExchange.getId())
                .decreasedCurrencyId(currencyExchange.getDecreasedCurrency().getId())
                .decreasedCurrencyCode(currencyExchange.getDecreasedCurrency().getCode())
                .amountDecreased(currencyExchange.getAmountDecreased())
                .increasedCurrencyId(currencyExchange.getIncreasedCurrency().getId())
                .increasedCurrencyCode(currencyExchange.getIncreasedCurrency().getCode())
                .amountIncreased(currencyExchange.getAmountIncreased())
                .exchangeDateTime(currencyExchange.getExchangeDateTime())
                .exchangeRate(currencyExchange.getExchangeRate())
                .status(currencyExchange.getCurrencyExchangeStatus())
                .build();
    }
}
