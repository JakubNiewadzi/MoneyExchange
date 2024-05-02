package pl.niewadzj.moneyExchange.api.currency.interfaces;

import pl.niewadzj.moneyExchange.api.currency.records.CurrencyResponse;

import java.util.List;

public interface CurrencyController {

    List<CurrencyResponse> getCurrencies();

    CurrencyResponse getCurrency(Long id);

    List<CurrencyResponse> getExchangeRatesByCurrency(Long id);
}
