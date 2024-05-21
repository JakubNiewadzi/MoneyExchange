package pl.niewadzj.moneyExchange.api.account.records;

import lombok.Builder;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.CurrencyAccountResponse;

import java.util.List;

@Builder
public record CurrencyAccountsPageResponse(List<CurrencyAccountResponse> currencyAccountResponses,
                                           long amount,
                                           int pages) {
}
