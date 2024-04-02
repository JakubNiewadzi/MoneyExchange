package pl.niewadzj.moneyExchange.api.transfer.records;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransferResponse(String currencyProvidedCode,
                               BigDecimal amountProvided,
                               String currencyReceivedCode,
                               BigDecimal amountReceived) {
}
