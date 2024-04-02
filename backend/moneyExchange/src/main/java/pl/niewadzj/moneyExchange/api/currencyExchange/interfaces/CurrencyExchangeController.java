package pl.niewadzj.moneyExchange.api.currencyExchange.interfaces;

import org.springframework.data.domain.Page;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.CurrencyExchangeResponse;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyRequest;
import pl.niewadzj.moneyExchange.api.currencyExchange.records.ExchangeCurrencyResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

public interface CurrencyExchangeController {
    ExchangeCurrencyResponse exchangeCurrency(ExchangeCurrencyRequest exchangeCurrencyRequest, User user);

    Page<CurrencyExchangeResponse> getExchangesHistoryForUser(int pageNo, int pageSize, User user);

    ExchangeCurrencyResponse revertExchange(Long id, User user);

}
