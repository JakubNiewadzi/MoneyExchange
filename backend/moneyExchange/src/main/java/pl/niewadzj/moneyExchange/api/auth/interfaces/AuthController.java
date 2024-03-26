package pl.niewadzj.moneyExchange.api.auth.interfaces;

import pl.niewadzj.moneyExchange.api.auth.records.LoginRequest;
import pl.niewadzj.moneyExchange.api.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.api.auth.records.TokenResponse;

public interface AuthController {

    TokenResponse register(RegistrationRequest registrationRequest);

    TokenResponse login(LoginRequest loginRequest);

}
