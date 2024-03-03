package pl.niewadzj.moneyExchange.api.currency.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyScheduler {

    private final ApiConsumer apiConsumer;

    @Scheduled(fixedRate = 1000L * 60L)
    public void getCurrencies() throws InterruptedException{
        try {
            apiConsumer.getCurrency();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
