package pl.niewadzj.moneyExchange.api.currency.interfaces;

import pl.niewadzj.moneyExchange.api.currency.records.CurrencyExternalResponse;
import pl.niewadzj.moneyExchange.api.currency.records.CurrencyResponse;
import pl.niewadzj.moneyExchange.entities.currency.Currency;

import java.util.List;

public interface CurrencyService {

    void addExchangeRates(List<Currency> currencies);
    void updateExchangeRates(List<Currency> currencies);
    List<CurrencyResponse> getCurrencies();
    CurrencyResponse getCurrency(Long id);
}
