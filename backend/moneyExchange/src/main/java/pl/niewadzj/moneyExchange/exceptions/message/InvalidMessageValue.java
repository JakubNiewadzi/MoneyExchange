package pl.niewadzj.moneyExchange.exceptions.message;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class InvalidMessageValue extends BadRequestException {
    public InvalidMessageValue() {
        super("Value must me more that zero, less than current exchange rate and at most " +
                "lower than current exchange rate by 1.0 of value");
    }
}
