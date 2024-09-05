package pl.niewadzj.moneyExchange.api.message.records;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_FRACTION;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_INTEGER;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_MIN_VALUE;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.AMOUNT_NULL_MSG;
import static pl.niewadzj.moneyExchange.api.currencyExchange.constants.CurrencyExchangeConstants.ID_NULL_MSG;

public record DateMessageRequest(String message,
                                 @NotNull(message = ID_NULL_MSG)
                                 Long sourceCurrencyId,
                                 @NotNull(message = ID_NULL_MSG)
                                 Long targetCurrencyId,
                                 @NotNull(message = AMOUNT_NULL_MSG)
                                 @DecimalMin(value = AMOUNT_MIN_VALUE)
                                 @Digits(integer = AMOUNT_INTEGER, fraction = AMOUNT_FRACTION)
                                 BigDecimal amount,
                                 LocalDateTime triggerDate) {
}
