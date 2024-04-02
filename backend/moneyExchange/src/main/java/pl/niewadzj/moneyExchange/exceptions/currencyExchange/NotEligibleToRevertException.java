package pl.niewadzj.moneyExchange.exceptions.currencyExchange;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class NotEligibleToRevertException extends BadRequestException {
    public NotEligibleToRevertException() {
        super("You are not eligible to revert this transaction");
    }
}
