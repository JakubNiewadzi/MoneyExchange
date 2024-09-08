package pl.niewadzj.moneyExchange.api.message.records;

import lombok.Builder;
import pl.niewadzj.moneyExchange.entities.message.ValueMessage;

import java.util.List;

@Builder
public record ValueMessagesResponse(List<ValueMessage> valueMessages,
                                    long amount,
                                    int pages) {
}
