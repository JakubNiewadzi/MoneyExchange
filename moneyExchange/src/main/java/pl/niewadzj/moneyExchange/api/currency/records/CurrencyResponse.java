package pl.niewadzj.moneyExchange.api.currency.records;

import java.util.Date;

public record CurrencyResponse(Long id,
                               String name,
                               String code,
                               Date rateDate,
                               double exchangeRate) {
}
