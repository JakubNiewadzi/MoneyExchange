package pl.niewadzj.moneyExchange.api.message;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.niewadzj.moneyExchange.api.message.interfaces.ValueMessageService;
import pl.niewadzj.moneyExchange.entities.message.ValueMessage;
import pl.niewadzj.moneyExchange.entities.message.repositories.ValueMessageRepository;

@Service
@RequiredArgsConstructor
public class ValueMessageServiceImpl implements ValueMessageService {

    private final ValueMessageRepository valueMessageRepository;

    @Override
    public void performValueCheckOnMessages() {
        Iterable<ValueMessage> valueMessages = valueMessageRepository.findAll();



    }

    @Override
    public void createValueMessage() {

    }
}
