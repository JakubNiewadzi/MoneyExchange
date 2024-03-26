package pl.niewadzj.moneyExchange.exceptions.currencyAccount;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class CurrencyAccountNotSuspendedException extends BadRequestException {
    public CurrencyAccountNotSuspendedException() {
        super("Currency account is not suspended, so it cannot be made active");
    }
}
