package pl.niewadzj.moneyExchange.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.auth.interfaces.AuthService;
import pl.niewadzj.moneyExchange.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.auth.records.TokenResponse;
import pl.niewadzj.moneyExchange.config.jwt.JwtService;
import pl.niewadzj.moneyExchange.entities.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.entities.user.UserRole;
import pl.niewadzj.moneyExchange.entities.user.interfaces.UserRepository;
import pl.niewadzj.moneyExchange.exceptions.auth.UserAlreadyExistsException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public TokenResponse register(RegistrationRequest registrationRequest) {
        log.debug("Registering user: {}", registrationRequest);

        checkIfUserExists(registrationRequest.email());

        User user = User.builder()
                .email(registrationRequest.email())
                .firstName(registrationRequest.firstName())
                .lastName(registrationRequest.lastName())
                .role(UserRole.USER)
                .password(passwordEncoder.encode(registrationRequest.password()))
                .build();

        userRepository.save(user);

        accountService.createAccount(user);

        String authToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return TokenResponse.builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void checkIfUserExists(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        userOptional.ifPresent(user -> {
            throw new UserAlreadyExistsException(email);
        });

    }
}
