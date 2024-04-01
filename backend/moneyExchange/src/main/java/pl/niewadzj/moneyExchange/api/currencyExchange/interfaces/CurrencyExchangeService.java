package pl.niewadzj.moneyExchange.api.currencyExchange.interfaces;

import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyExchangeRequest;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyExchangeResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface CurrencyExchangeService {
    CurrencyExchangeResponse exchangeCurrency(CurrencyExchangeRequest currencyExchangeRequest, User user);
}
