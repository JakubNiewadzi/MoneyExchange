package pl.niewadzj.moneyExchange.auth.records;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;

import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MAX_NAME;
import static pl.niewadzj.moneyExchange.auth.constants.AuthConstants.MIN_NAME;

@Builder
public record RegistrationRequest(
        @NotNull
        @NotBlank
        @Size(min = MIN_NAME, max = MAX_NAME)
        String firstname,
        @NotNull
        @NotBlank
        @Size(min = MIN_NAME, max = MAX_NAME)
        String lastname,
        @NotNull
        @NotBlank
        String password,
        @NotNull
        @NotBlank
        @Email
        String email) {
}
