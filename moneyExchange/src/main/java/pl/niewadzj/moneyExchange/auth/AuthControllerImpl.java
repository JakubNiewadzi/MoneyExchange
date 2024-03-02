package pl.niewadzj.moneyExchange.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.auth.interfaces.AuthController;
import pl.niewadzj.moneyExchange.auth.interfaces.AuthService;
import pl.niewadzj.moneyExchange.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.auth.records.TokenResponse;

import static pl.niewadzj.moneyExchange.auth.constants.AuthMappings.AUTH_MAPPING;
import static pl.niewadzj.moneyExchange.auth.constants.AuthMappings.REGISTER_MAPPING;

@RestController
@RequestMapping(AUTH_MAPPING)
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping(REGISTER_MAPPING)
    public TokenResponse register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return authService.register(registrationRequest);
    }
}
