package pl.niewadzj.moneyExchange.exceptions.currencyAccount;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class CurrencyAccountSuspendedException extends BadRequestException {
    public CurrencyAccountSuspendedException() {
        super("Given currency account is suspended, activate it to perform action");
    }
}
