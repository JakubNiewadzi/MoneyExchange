package pl.niewadzj.moneyExchange.api.auth.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import pl.niewadzj.moneyExchange.api.auth.constants.AuthConstants;

@Builder
public record RegistrationRequest(
        @NotNull(message = AuthConstants.STRING_NOT_NULL)
        @NotBlank(message = AuthConstants.STRING_NOT_BLANK)
        @Size(min = AuthConstants.MIN_NAME, max = AuthConstants.MAX_NAME, message = AuthConstants.STRING_SIZE)
        String firstName,
        @NotNull(message = AuthConstants.STRING_NOT_NULL)
        @NotBlank(message = AuthConstants.STRING_NOT_BLANK)
        @Size(min = AuthConstants.MIN_NAME, max = AuthConstants.MAX_NAME, message = AuthConstants.STRING_SIZE)
        String lastName,
        @NotNull(message = AuthConstants.STRING_NOT_NULL)
        @NotBlank(message = AuthConstants.STRING_NOT_BLANK)
        @Size(min = AuthConstants.MIN_PASSWORD, max = AuthConstants.MAX_PASSWORD, message = AuthConstants.PASSWORD_SIZE)
        String password,
        @NotNull(message = AuthConstants.STRING_NOT_NULL)
        @NotBlank(message = AuthConstants.STRING_NOT_BLANK)
        @Email(message = AuthConstants.EMAIL_MESSAGE)
        String email
) {
}
