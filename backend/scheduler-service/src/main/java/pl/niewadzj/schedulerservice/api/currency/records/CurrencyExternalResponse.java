package pl.niewadzj.schedulerservice.api.currency.records;

import lombok.Builder;

@Builder
public record CurrencyExternalResponse(String currency,
                                       String code,
                                       Float mid) {
}
