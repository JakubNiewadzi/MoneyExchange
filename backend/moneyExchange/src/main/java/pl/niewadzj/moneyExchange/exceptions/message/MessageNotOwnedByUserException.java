package pl.niewadzj.moneyExchange.exceptions.message;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class MessageNotOwnedByUserException extends BadRequestException {
    public MessageNotOwnedByUserException() {
        super("This message could not have been edited, cause the user does not own it");
    }
}
