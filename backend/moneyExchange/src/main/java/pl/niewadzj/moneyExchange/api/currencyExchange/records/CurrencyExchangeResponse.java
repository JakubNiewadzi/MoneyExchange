package pl.niewadzj.moneyExchange.api.currencyExchange.records;

import lombok.Builder;
import pl.niewadzj.moneyExchange.entities.currencyExchange.CurrencyExchangeStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record CurrencyExchangeResponse(Long id,
                                       Long decreasedCurrencyId,
                                       String decreasedCurrencyCode,
                                       BigDecimal amountDecreased,
                                       Long increasedCurrencyId,
                                       String increasedCurrencyCode,
                                       BigDecimal amountIncreased,
                                       BigDecimal exchangeRate,
                                       CurrencyExchangeStatus status,
                                       LocalDateTime exchangeDateTime) {
}
