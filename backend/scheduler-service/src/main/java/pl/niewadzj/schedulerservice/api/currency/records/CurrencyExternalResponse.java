package pl.niewadzj.schedulerservice.api.currency.records;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CurrencyExternalResponse(String currency,
                                       String code,
                                       BigDecimal mid) {
}
