package pl.niewadzj.moneyExchange.api.currencyExchange.records;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ExchangeCurrencyRequest(
        @NotNull
        Long currencyFromId,
        @NotNull
        Long currencyToId,
        @NotNull
        @DecimalMin(value = "0.0")
        @Digits(integer = 10, fraction = 2)
        BigDecimal amount
) {
}