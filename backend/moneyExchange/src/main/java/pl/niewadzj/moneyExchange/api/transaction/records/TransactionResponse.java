package pl.niewadzj.moneyExchange.api.transaction.records;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(Long decreasedCurrencyId,
                                  String decreasedCurrencyCode,
                                  BigDecimal decreasedCurrencyBalance,
                                  Long increasedCurrencyId,
                                  String increasedCurrencyCode,
                                  BigDecimal increasedCurrencyBalance,
                                  LocalDateTime transactionDate) {
}
