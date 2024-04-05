package pl.niewadzj.moneyExchange.api.transfer.records;

import lombok.Builder;
import pl.niewadzj.moneyExchange.entities.transfer.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransferResponse(Long id,
                               String providerAccountNumber,
                               String receiverAccountNumber,
                               String providerCurrencyCode,
                               String receiverCurrencyCode,
                               LocalDateTime transferDateTime,
                               BigDecimal currencyProvided,
                               BigDecimal currencyReceived,
                               BigDecimal exchangeRate,
                               TransferStatus transferStatus) {
}
