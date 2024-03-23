package pl.niewadzj.moneyExchange.api.transaction.records;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public record TransactionRequest(
        @NotNull
        Long currencyFromId,
        @NotNull
        Long currencyToId,
        @NotNull
        @Digits(integer = 10, fraction = 2)
        Float amount
) {
}
