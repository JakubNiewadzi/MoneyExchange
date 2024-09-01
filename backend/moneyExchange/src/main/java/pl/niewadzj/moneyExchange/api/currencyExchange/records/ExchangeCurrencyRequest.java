package pl.niewadzj.moneyExchange.api.currencyExchange.records;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_FRACTION;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_INTEGER;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_MIN_VALUE;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_NULL_MSG;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.ID_NULL_MSG;

@Builder
public record ExchangeCurrencyRequest(
        @NotNull(message = ID_NULL_MSG)
        Long currencyFromId,
        @NotNull(message = ID_NULL_MSG)
        Long currencyToId,
        @NotNull(message = AMOUNT_NULL_MSG)
        @DecimalMin(value = AMOUNT_MIN_VALUE)
        @Digits(integer = AMOUNT_INTEGER, fraction = AMOUNT_FRACTION)
        BigDecimal amount
) {
}
