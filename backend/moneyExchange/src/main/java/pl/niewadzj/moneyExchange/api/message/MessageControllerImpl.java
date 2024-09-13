package pl.niewadzj.moneyExchange.api.message;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.niewadzj.moneyExchange.api.message.interfaces.DateMessageService;
import pl.niewadzj.moneyExchange.api.message.interfaces.MessageController;
import pl.niewadzj.moneyExchange.api.message.interfaces.ValueMessageService;
import pl.niewadzj.moneyExchange.api.message.records.DateMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.DateMessagesResponse;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessageRequest;
import pl.niewadzj.moneyExchange.api.message.records.ValueMessagesResponse;
import pl.niewadzj.moneyExchange.entities.user.User;

import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.CREATE_DATE_MESSAGE;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.CREATE_VALUE_MESSAGE;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.DELETE_DATE_MESSAGE;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.DELETE_VALUE_MESSAGE;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.GET_DATE_MESSAGES;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.GET_VALUE_MESSAGES;
import static pl.niewadzj.moneyExchange.api.message.constants.MessageMappings.MESSAGE_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(MESSAGE_MAPPING)
public class MessageControllerImpl implements MessageController {

    private final DateMessageService dateMessageService;
    private final ValueMessageService valueMessageService;


    @Override
    @PostMapping(CREATE_DATE_MESSAGE)
    public void createDateMessage(@RequestBody @Valid DateMessageRequest dateMessageRequest,
                                  @AuthenticationPrincipal User user) {
        dateMessageService.createDateMessage(dateMessageRequest, user);
    }

    @Override
    @PostMapping(CREATE_VALUE_MESSAGE)
    public void createValueMessage(@RequestBody @Valid ValueMessageRequest valueMessageRequest,
                                   @AuthenticationPrincipal User user) {
        valueMessageService.createValueMessage(valueMessageRequest, user);
    }

    @Override
    @GetMapping(GET_DATE_MESSAGES)
    public DateMessagesResponse getDateMessages(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                @AuthenticationPrincipal User user) {
        return dateMessageService.getDateMessageResponses(pageNo, pageSize, user);
    }

    @Override
    @GetMapping(GET_VALUE_MESSAGES)
    public ValueMessagesResponse getValueMessages(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                  @RequestParam(defaultValue = "10", required = false) int pageSize,
                                                  @AuthenticationPrincipal User user) {
        return valueMessageService.getValueMessageResponses(pageNo, pageSize, user);
    }

    @Override
    @DeleteMapping(DELETE_DATE_MESSAGE)
    public void deleteDateMessage(@RequestParam Long id, @AuthenticationPrincipal User user) {
        dateMessageService.deleteDateMessage(id, user);
    }

    @Override
    @DeleteMapping(DELETE_VALUE_MESSAGE)
    public void deleteValueMessage(@RequestParam Long id, @AuthenticationPrincipal User user) {
        valueMessageService.deleteValueMessage(id, user);
    }
}
