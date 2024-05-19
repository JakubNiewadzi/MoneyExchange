package pl.niewadzj.moneyExchange.api.currencyExchange.records;

import lombok.Builder;

import java.util.List;

@Builder
public record CurrencyHistoryResponse(List<CurrencyExchangeResponse> currencyExchangeResponses,
                                      long amount,
                                      int pages) {
}
