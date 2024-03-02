package pl.niewadzj.moneyExchange.auth.interfaces;

import pl.niewadzj.moneyExchange.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.auth.records.TokenResponse;

public interface AuthController {

    TokenResponse register(RegistrationRequest registrationRequest);

}
