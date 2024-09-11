package pl.niewadzj.moneyExchange.exceptions.message;

import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class ValueMessageNotFoundException extends NotFoundException {

    public ValueMessageNotFoundException(Long id) {
        super("Value message with id %s could not have been found".formatted(id));
    }

}
