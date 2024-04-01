package pl.niewadzj.moneyExchange.api.currencyExchange.records;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record CurrencyExchangeResponse(Long decreasedCurrencyId,
                                       String decreasedCurrencyCode,
                                       BigDecimal decreasedCurrencyBalance,
                                       Long increasedCurrencyId,
                                       String increasedCurrencyCode,
                                       BigDecimal increasedCurrencyBalance,
                                       LocalDateTime transactionDate) {
}
