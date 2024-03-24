package pl.niewadzj.moneyExchange.api.currency.records;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CurrencyResponse(Long id,
                               String name,
                               String code,
                               LocalDateTime rateDate,
                               BigDecimal exchangeRate) {
}
