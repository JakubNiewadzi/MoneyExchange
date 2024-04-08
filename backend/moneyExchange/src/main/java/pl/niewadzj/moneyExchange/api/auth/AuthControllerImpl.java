package pl.niewadzj.moneyExchange.api.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.auth.interfaces.AuthController;
import pl.niewadzj.moneyExchange.api.auth.interfaces.AuthService;
import pl.niewadzj.moneyExchange.api.auth.records.LoginRequest;
import pl.niewadzj.moneyExchange.api.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.api.auth.records.TokenResponse;

import static pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings.AUTH_MAPPING;
import static pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings.LOGIN_MAPPING;
import static pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings.REFRESH_AUTH_TOKEN;
import static pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings.REGISTER_MAPPING;

@RestController
@RequestMapping(AUTH_MAPPING)
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    @PostMapping(REGISTER_MAPPING)
    public final TokenResponse register(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return authService.register(registrationRequest);
    }

    @Override
    @PostMapping(LOGIN_MAPPING)
    public final TokenResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @Override
    @GetMapping(REFRESH_AUTH_TOKEN)
    public final TokenResponse refreshAuthToken(@RequestParam String refreshToken) {
        return authService.refreshAuthToken(refreshToken);
    }
}
