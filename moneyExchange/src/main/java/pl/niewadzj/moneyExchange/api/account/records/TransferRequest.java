package pl.niewadzj.moneyExchange.api.account.records;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import static pl.niewadzj.moneyExchange.api.account.constants.BadRequestMessages.INCORRECT_AMOUNT_MSG;
import static pl.niewadzj.moneyExchange.api.account.constants.BadRequestMessages.NEGATIVE_AMOUNT_MSG;

@Builder
public record TransferRequest(@NotNull Long currencyId,
                              @Positive(message = NEGATIVE_AMOUNT_MSG)
                              @Digits(integer = 10, fraction = 2, message = INCORRECT_AMOUNT_MSG)
                              Float amount) {
}
