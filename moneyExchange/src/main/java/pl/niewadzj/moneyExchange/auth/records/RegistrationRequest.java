package pl.niewadzj.moneyExchange.auth.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;

import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.EMAIL_MESSAGE;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MAX_NAME;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MAX_PASSWORD;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MIN_NAME;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MIN_PASSWORD;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.PASSWORD_SIZE;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.STRING_NOT_BLANK;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.STRING_NOT_NULL;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.STRING_SIZE;

@Builder
public record RegistrationRequest(
        @NotNull(message = STRING_NOT_NULL)
        @NotBlank(message = STRING_NOT_BLANK)
        @Size(min = MIN_NAME, max = MAX_NAME, message = STRING_SIZE)
        String firstName,
        @NotNull(message = STRING_NOT_NULL)
        @NotBlank(message = STRING_NOT_BLANK)
        @Size(min = MIN_NAME, max = MAX_NAME, message = STRING_SIZE)
        String lastName,
        @NotNull(message = STRING_NOT_NULL)
        @NotBlank(message = STRING_NOT_BLANK)
        @Size(min = MIN_PASSWORD, max = MAX_PASSWORD, message = PASSWORD_SIZE)
        String password,
        @NotNull(message = STRING_NOT_NULL)
        @NotBlank(message = STRING_NOT_BLANK)
        @Email(message = EMAIL_MESSAGE)
        String email
) {
}
