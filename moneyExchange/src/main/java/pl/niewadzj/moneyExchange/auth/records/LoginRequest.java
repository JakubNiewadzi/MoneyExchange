package pl.niewadzj.moneyExchange.auth.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.EMAIL_MESSAGE;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MAX_PASSWORD;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MIN_PASSWORD;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.PASSWORD_SIZE;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.STRING_NOT_BLANK;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.STRING_NOT_NULL;

public record LoginRequest(
        @NotNull(message = STRING_NOT_NULL)
        @NotBlank(message = STRING_NOT_BLANK)
        @Email(message = EMAIL_MESSAGE)
        String email,
        @NotNull(message = STRING_NOT_NULL)
        @NotBlank(message = STRING_NOT_BLANK)
        @Size(min = MIN_PASSWORD, max = MAX_PASSWORD, message = PASSWORD_SIZE)
        String password) {
}
