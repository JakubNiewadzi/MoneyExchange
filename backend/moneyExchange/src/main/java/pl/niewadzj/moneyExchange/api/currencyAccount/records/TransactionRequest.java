package pl.niewadzj.moneyExchange.api.currencyAccount.records;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountConstants.AMOUNT_FRACTION;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountConstants.AMOUNT_INTEGER;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountConstants.ID_NULL_MSG;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountConstants.INCORRECT_AMOUNT_MSG;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountConstants.MINIMAL_AMOUNT;
import static pl.niewadzj.moneyExchange.api.currencyAccount.constants.CurrencyAccountConstants.NEGATIVE_AMOUNT_MSG;

@Builder
public record TransactionRequest(@NotNull(message = ID_NULL_MSG) Long currencyId,
                                 @DecimalMin(value = MINIMAL_AMOUNT, message = NEGATIVE_AMOUNT_MSG)
                                 @Digits(integer = AMOUNT_INTEGER, fraction = AMOUNT_FRACTION, message = INCORRECT_AMOUNT_MSG)
                                 BigDecimal amount) {
}
