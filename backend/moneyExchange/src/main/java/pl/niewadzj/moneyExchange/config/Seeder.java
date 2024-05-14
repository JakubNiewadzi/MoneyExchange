package pl.niewadzj.moneyExchange.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import pl.niewadzj.moneyExchange.api.auth.interfaces.AuthService;
import pl.niewadzj.moneyExchange.api.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.api.currencyAccount.interfaces.CurrencyAccountService;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.TransactionRequest;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.entities.user.UserRole;
import pl.niewadzj.moneyExchange.entities.user.interfaces.UserRepository;
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountNotActiveException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@Configuration
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final CurrencyAccountService currencyAccountService;
    private final Random random = new Random(0);
    private final CurrencyRepository currencyRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        List<User> sampleUsers = createSampleUsers();
        List<Currency> currencies = currencyRepository.findAll();
        if(currencies.isEmpty()){
            return;
        }

        for (User user : sampleUsers) {
            for (int i = 0; i < 3; i++) {
                currencyAccountService.activateCurrencyAccount(getRandom(currencies).getId(), user);
                currencyAccountService.depositToAccount(new TransactionRequest(getRandom(currencies).getId(),
                        BigDecimal.valueOf(random.nextDouble(0, 1000.25))), user);
                try {
                    currencyAccountService.suspendCurrencyAccount(getRandom(currencies).getId(), user);
                } catch (CurrencyAccountNotActiveException e){}
            }
        }
    }


    private List<User> createSampleUsers() {
        try {
            authService.register(new RegistrationRequest("Jan", "Kowalski", "#Silnehaslo1", "jan.kowalski@gmail.com"));
            authService.register(new RegistrationRequest("Alice", "Smith", "#Silnehaslo1", "alice.smith@example.com"));
            authService.register(new RegistrationRequest("Bob", "Johnson", "#Silnehaslo1", "bob.johnson@example.com"));
            authService.register(new RegistrationRequest("Emily", "Davis", "#Silnehaslo1", "emily.davis@example.com"));
            authService.register(new RegistrationRequest("Michael", "Brown", "#Silnehaslo1", "michael.brown@example.com"));
            authService.register(new RegistrationRequest("Sophia", "Wilson", "#Silnehaslo1", "sophia.wilson@example.com"));
        }catch(Exception e){

        }
        User admin = userRepository.findByEmail("jan.kowalski@gmail.com").orElseThrow();
        admin.setRole(UserRole.ADMIN);

        userRepository.save(admin);

        return userRepository.findAll();
    }

    private <T> T getRandom(List<T> list) {
        return list.get(random.nextInt(0, list.size()));
    }
}
