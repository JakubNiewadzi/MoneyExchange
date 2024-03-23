package pl.niewadzj.schedulerservice.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.niewadzj.schedulerservice.api.currency.interfaces.CurrencyService;

@Component
@EnableAsync
@RequiredArgsConstructor
public class CurrencyScheduler {

    private final ApiConsumer apiConsumer;
    private final CurrencyService currencyService;

    @Async
    @Scheduled(initialDelay = 0)
    public void postCurrencies(){
        currencyService.addExchangeRates(apiConsumer.getCurrency());
    }

    @Async
    @Scheduled(initialDelay = 1000L * 60L,
            fixedRate = 1000L * 60L)
    public void updateCurrencies(){
        currencyService.updateExchangeRates(apiConsumer.getCurrency());
    }
}
