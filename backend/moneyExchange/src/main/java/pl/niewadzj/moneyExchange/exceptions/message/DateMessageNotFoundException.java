package pl.niewadzj.moneyExchange.exceptions.message;

import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class DateMessageNotFoundException extends NotFoundException {
    public DateMessageNotFoundException(Long id) {
        super("Date message with id %s could not have been found".formatted(id));
    }
}
