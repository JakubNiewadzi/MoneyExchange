package pl.niewadzj.moneyExchange.api.currency.interfaces;

import pl.niewadzj.moneyExchange.api.currency.records.CurrencyResponse;

import java.util.List;

public interface CurrencyService {

    List<CurrencyResponse> getCurrencies();
    CurrencyResponse getCurrency(Long id);
}
