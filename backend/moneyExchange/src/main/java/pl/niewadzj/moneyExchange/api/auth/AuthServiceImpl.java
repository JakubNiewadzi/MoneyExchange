package pl.niewadzj.moneyExchange.api.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.account.interfaces.AccountService;
import pl.niewadzj.moneyExchange.api.auth.interfaces.AuthService;
import pl.niewadzj.moneyExchange.api.auth.records.LoginRequest;
import pl.niewadzj.moneyExchange.api.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.api.auth.records.TokenResponse;
import pl.niewadzj.moneyExchange.config.jwt.JwtService;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.entities.user.UserRole;
import pl.niewadzj.moneyExchange.entities.user.interfaces.UserRepository;
import pl.niewadzj.moneyExchange.exceptions.auth.PasswordsDoNotMatchException;
import pl.niewadzj.moneyExchange.exceptions.auth.UserAlreadyExistsException;
import pl.niewadzj.moneyExchange.exceptions.auth.UserNotFoundException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public TokenResponse register(RegistrationRequest registrationRequest) {
        log.debug("Registering user: {}", registrationRequest);
        System.out.println("USer repo: " + userRepository);
        log.info("Repo: {}", userRepository);
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

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        log.debug("Processing login request: {}", loginRequest);

        User user = userRepository
                .findByEmail(loginRequest.email())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.email()));


        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new PasswordsDoNotMatchException();
        }

        final String authToken = jwtService.generateToken(user);
        final String refreshToken = jwtService.generateRefreshToken(user);

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
