package pl.niewadzj.moneyExchange.api.transfer.records;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record TransferRequest(@NotNull
                              Long providerCurrencyId,
                              @NotNull
                              @DecimalMin(value = "0.0")
                              @Digits(integer = 10, fraction = 2)
                              BigDecimal amountProvided,
                              @NotNull
                              @Pattern(regexp="\\d{26}")
                              String receiverAccountNumber,
                              @NotNull
                              Long receiverCurrencyId) {
}
