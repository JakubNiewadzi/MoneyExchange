package pl.niewadzj.moneyExchange.api.currency.records;

import java.time.LocalDateTime;

public record CurrencyResponse(Long id,
                               String name,
                               String code,
                               LocalDateTime rateDate,
                               double exchangeRate) {
}
