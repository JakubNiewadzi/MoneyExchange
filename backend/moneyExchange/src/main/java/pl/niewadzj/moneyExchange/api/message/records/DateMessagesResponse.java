package pl.niewadzj.moneyExchange.api.message.records;

import lombok.Builder;
import pl.niewadzj.moneyExchange.entities.message.DateMessage;

import java.util.List;

@Builder
public record DateMessagesResponse(List<DateMessage> dateMessages,
                                   long amount,
                                   int pages) {
}
