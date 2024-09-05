package pl.niewadzj.moneyExchange.api.message;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.message.interfaces.DateMessageService;
import pl.niewadzj.moneyExchange.api.message.interfaces.MessageController;
import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageRequest;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.CREATE_DATE_MESSAGE;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.MESSAGE_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(MESSAGE_MAPPING)
public class MessageControllerImpl implements MessageController {

    private final DateMessageService dateMessageService;
    private final ValueMessageRequest valueMessageRequest;


    @Override
    @PostMapping(CREATE_DATE_MESSAGE)
    public void createDateMessage(@RequestBody @Valid DateMessageRequest dateMessageRequest,
                                  @AuthenticationPrincipal User user) {
        dateMessageService.createDateMessage(dateMessageRequest, user);
    }

    @Override
    public void createValueMessage(ValueMessageRequest valueMessageRequest, User user) {

    }
}
