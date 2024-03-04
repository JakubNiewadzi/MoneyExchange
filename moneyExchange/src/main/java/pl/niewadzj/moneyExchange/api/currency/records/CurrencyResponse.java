package pl.niewadzj.moneyExchange.api.currency.records;

import lombok.Builder;

@Builder
public record CurrencyResponse(String currency,
                               String code,
                               double mid) {
}
