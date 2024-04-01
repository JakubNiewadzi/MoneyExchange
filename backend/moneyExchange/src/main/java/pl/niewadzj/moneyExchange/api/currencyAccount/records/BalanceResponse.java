package pl.niewadzj.moneyExchange.api.currencyAccount.records;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BalanceResponse(Long currencyId,
                              String currencyCode,
                              BigDecimal balance) {
}
