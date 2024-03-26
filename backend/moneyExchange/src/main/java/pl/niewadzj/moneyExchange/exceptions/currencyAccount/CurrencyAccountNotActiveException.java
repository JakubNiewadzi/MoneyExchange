package pl.niewadzj.moneyExchange.exceptions.currencyAccount;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class CurrencyAccountNotActiveException extends BadRequestException {
    public CurrencyAccountNotActiveException() {
        super("Currency cannot be suspended because it is not active");
    }
}
