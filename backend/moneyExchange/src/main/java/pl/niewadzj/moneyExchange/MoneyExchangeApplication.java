package pl.niewadzj.moneyExchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MoneyExchangeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyExchangeApplication.class, args);
    }

}
