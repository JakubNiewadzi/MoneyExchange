package pl.niewadzj.moneyExchange.exceptions.currencyExchange;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class TooLateToRevertException extends BadRequestException {
    public TooLateToRevertException() {
        super("You can revert transaction only within the first hour of making it");
    }
}
