package pl.niewadzj.moneyExchange.auth.interfaces;

import pl.niewadzj.moneyExchange.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.auth.records.TokenResponse;

public interface AuthService {

    TokenResponse register(RegistrationRequest registrationRequest);

}
