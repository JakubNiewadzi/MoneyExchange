package pl.niewadzj.moneyExchange.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import pl.niewadzj.moneyExchange.api.auth.interfaces.AuthService;
import pl.niewadzj.moneyExchange.api.auth.records.RegistrationRequest;
import pl.niewadzj.moneyExchange.api.currencyAccount.interfaces.CurrencyAccountService;
import pl.niewadzj.moneyExchange.api.currencyAccount.records.TransactionRequest;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageRequest;
import pl.niewadzj.moneyExchange.entities.currency.Currency;
import pl.niewadzj.moneyExchange.entities.currency.interfaces.CurrencyRepository;
import pl.niewadzj.moneyExchange.entities.currencyAccount.interfaces.CurrencyAccountRepository;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;
import pl.niewadzj.moneyExchange.entities.message.ValueMessage;
import pl.niewadzj.moneyExchange.entities.message.repositories.DateMessageRepository;
import pl.niewadzj.moneyExchange.entities.message.repositories.ValueMessageRepository;
import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.entities.user.UserRole;
import pl.niewadzj.moneyExchange.entities.user.interfaces.UserRepository;
import pl.niewadzj.moneyExchange.exceptions.currencyAccount.CurrencyAccountNotActiveException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class Seeder implements CommandLineRunner {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final Random random = new Random(0);
    private final CurrencyRepository currencyRepository;
    private final DateMessageRepository dateMessageRepository;
    private final CurrencyAccountService currencyAccountService;
    private final ValueMessageRepository valueMessageRepository;


    @Override
    @Transactional
    public void run(String... args) {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Europe/Warsaw")));

        List<User> sampleUsers = createSampleUsers();
        List<Currency> currencies = currencyRepository.findAll();
        if (currencies.isEmpty()) {
            return;
        }

        for (User user : sampleUsers) {
            for (int i = 0; i < 3; i++) {
                currencyAccountService.activateCurrencyAccount(getRandom(currencies).getId(), user);
                currencyAccountService.depositToAccount(new TransactionRequest(getRandom(currencies).getId(),
                        BigDecimal.valueOf(random.nextDouble(0, 1000.25))), user);
                try {
                    currencyAccountService.suspendCurrencyAccount(getRandom(currencies).getId(), user);
                } catch (CurrencyAccountNotActiveException e) {
                }
            }
        }

        createDateMessage();
        createValueMessage();
    }


    private List<User> createSampleUsers() {
        try {
            authService.register(new RegistrationRequest("Jan", "Kowalski", "#Silnehaslo1", "jan.kowalski@gmail.com"));
            authService.register(new RegistrationRequest("Alice", "Smith", "#Silnehaslo1", "alice.smith@example.com"));
            authService.register(new RegistrationRequest("Bob", "Johnson", "#Silnehaslo1", "bob.johnson@example.com"));
            authService.register(new RegistrationRequest("Emily", "Davis", "#Silnehaslo1", "emily.davis@example.com"));
            authService.register(new RegistrationRequest("Michael", "Brown", "#Silnehaslo1", "michael.brown@example.com"));
            authService.register(new RegistrationRequest("Sophia", "Wilson", "#Silnehaslo1", "sophia.wilson@example.com"));
        } catch (Exception e) {

        }
        User admin = userRepository.findByEmail("jan.kowalski@gmail.com").orElseThrow();
        admin.setRole(UserRole.ADMIN);

        userRepository.save(admin);

        return userRepository.findAll();
    }

    private void createDateMessage() {
        DateMessage dateMessage = DateMessage
                .builder()
                .sourceCurrencyId(12L)
                .targetCurrencyId(2L)
                .amount(BigDecimal.ONE)
                .userId(1L)
                .message("Date message 1")
                .triggerDate(LocalDateTime.now().minusHours(3))
                .build();


        dateMessageRepository.save(dateMessage);
        dateMessageRepository.findAll().forEach(System.out::println);

        DateMessage dateMessage2 = DateMessage
                .builder()
                .sourceCurrencyId(12L)
                .targetCurrencyId(4L)
                .amount(BigDecimal.TWO)
                .userId(1L)
                .message("Date message 1")
                .triggerDate(LocalDateTime.now().plusMinutes(2))
                .build();

        dateMessageRepository.save(dateMessage2);
    }

    private void createValueMessage(){
        ValueMessage valueMessage = ValueMessage
                .builder()
                .sourceCurrencyId(12L)
                .targetCurrencyId(1L)
                .amount(BigDecimal.ONE)
                .userId(1L)
                .message("Date message 1")
                .value(BigDecimal.valueOf(10.0))
                .build();

        valueMessageRepository.save(valueMessage);
        valueMessageRepository.findAll().forEach(System.out::println);
    }


    private <T> T getRandom(List<T> list) {
        return list.get(random.nextInt(0, list.size()));
    }
}
