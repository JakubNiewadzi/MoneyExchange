package pl.niewadzj.moneyExchange.api.currencyAccount.records;

import lombok.Builder;
import pl.niewadzj.moneyExchange.entities.currencyAccount.CurrencyAccountStatus;

import java.math.BigDecimal;

@Builder
public record CurrencyAccountResponse(Long id,
                                      BigDecimal balance,
                                      CurrencyAccountStatus status,
                                      Long currencyId,
                                      String currencyCode) {
}
