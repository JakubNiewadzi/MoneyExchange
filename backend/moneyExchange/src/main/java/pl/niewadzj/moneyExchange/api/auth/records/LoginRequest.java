package pl.niewadzj.moneyExchange.api.auth.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import pl.niewadzj.moneyExchange.api.auth.constants.AuthConstants;

public record LoginRequest(
        @NotNull(message = AuthConstants.STRING_NOT_NULL)
        @NotBlank(message = AuthConstants.STRING_NOT_BLANK)
        @Email(message = AuthConstants.EMAIL_MESSAGE)
        String email,
        @NotNull(message = AuthConstants.STRING_NOT_NULL)
        @NotBlank(message = AuthConstants.STRING_NOT_BLANK)
        @Size(min = AuthConstants.MIN_PASSWORD, max = AuthConstants.MAX_PASSWORD, message = AuthConstants.PASSWORD_SIZE)
        String password) {
}
