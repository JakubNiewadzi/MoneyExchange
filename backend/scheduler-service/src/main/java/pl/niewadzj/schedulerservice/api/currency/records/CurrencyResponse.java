package pl.niewadzj.schedulerservice.api.currency.records;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record CurrencyResponse(Long id,
                               String name,
                               String code,
                               LocalDateTime rateDate,
                               BigDecimal exchangeRate) {
}