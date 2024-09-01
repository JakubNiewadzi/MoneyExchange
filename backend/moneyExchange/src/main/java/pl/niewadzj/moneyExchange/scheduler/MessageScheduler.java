package pl.niewadzj.moneyExchange.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.niewadzj.moneyExchange.api.message.interfaces.DateMessageService;

@Component
@EnableAsync
@RequiredArgsConstructor
public class MessageScheduler {

    private final DateMessageService dateMessageService;

    @Async
    @Scheduled(initialDelay = 1000L * 10L,
            fixedRate = 1000L * 60L)
    public void performDueTransactions() {
        System.out.println("elo");
        dateMessageService.performDueTransactions();
    }
}
