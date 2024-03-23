package pl.niewadzj.moneyExchange.api.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings;
import pl.niewadzj.moneyExchange.api.auth.interfaces.AuthController;
import pl.niewadzj.moneyExchange.api.auth.records.LoginRequest;
import pl.niewadzj.moneyExchange.api.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.api.auth.records.TokenResponse;
import pl.niewadzj.moneyExchange.api.auth.interfaces.AuthService;

import static pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings.AUTH_MAPPING;
import static pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings.LOGIN_MAPPING;
import static pl.niewadzj.moneyExchange.api.auth.constants.AuthMappings.REGISTER_MAPPING;

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

    @Override
    @PostMapping(LOGIN_MAPPING)
    public TokenResponse login(@RequestBody @Valid LoginRequest loginRequest){
        return authService.login(loginRequest);
    }
}
