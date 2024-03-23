package pl.niewadzj.schedulerservice.api.currency.interfaces;

import pl.niewadzj.schedulerservice.entities.currency.Currency;

import java.util.List;

public interface CurrencyService {
    void addExchangeRates(List<Currency> currency);

    void updateExchangeRates(List<Currency> currency);
}
