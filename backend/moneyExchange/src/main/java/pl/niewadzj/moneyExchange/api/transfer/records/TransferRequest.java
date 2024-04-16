package pl.niewadzj.moneyExchange.api.transfer.records;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.ACCOUNT_NUMBER_NULL;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.ACCOUNT_NUMBER_REGEX;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.AMOUNT_FRACTION;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.AMOUNT_INTEGER;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.AMOUNT_NULL;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.ID_NULL_MSG;
import static pl.niewadzj.moneyExchange.api.transfer.constants.TransferConstants.MINIMAL_AMOUNT;

public record TransferRequest(@NotNull(message = ID_NULL_MSG)
                              Long providerCurrencyId,
                              @NotNull(message = AMOUNT_NULL)
                              @DecimalMin(value = MINIMAL_AMOUNT)
                              @Digits(integer = AMOUNT_INTEGER, fraction = AMOUNT_FRACTION)
                              BigDecimal amountProvided,
                              @NotNull(message = ACCOUNT_NUMBER_NULL)
                              @Pattern(regexp = ACCOUNT_NUMBER_REGEX)
                              String receiverAccountNumber,
                              @NotNull(message = ID_NULL_MSG)
                              Long receiverCurrencyId) {
}
