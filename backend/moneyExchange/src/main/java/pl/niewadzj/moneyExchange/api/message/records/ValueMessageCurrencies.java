package pl.niewadzj.moneyExchange.api.message.records;

import lombok.Builder;

@Builder
public record ValueMessageCurrencies(Long sourceCurrencyId,
                                     Long targetCurrencyId) {
}
