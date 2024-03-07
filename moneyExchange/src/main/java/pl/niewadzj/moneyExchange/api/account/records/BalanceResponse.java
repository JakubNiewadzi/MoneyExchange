package pl.niewadzj.moneyExchange.api.account.records;

import lombok.Builder;

@Builder
public record BalanceResponse(Long currencyId,
                              String currencyCode,
                              Float balance) {
}
