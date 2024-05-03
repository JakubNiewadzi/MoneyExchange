package pl.niewadzj.moneyExchange.exceptions.currencyAccount;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class CurrencyAccountAlreadyActive extends BadRequestException {
    public CurrencyAccountAlreadyActive() {
        super("Currency account is already active, so it cannot be made active");
    }
}
