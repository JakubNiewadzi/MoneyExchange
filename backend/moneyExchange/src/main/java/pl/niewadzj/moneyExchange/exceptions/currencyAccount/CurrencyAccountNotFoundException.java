package pl.niewadzj.moneyExchange.exceptions.currencyAccount;

import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class CurrencyAccountNotFoundException extends NotFoundException {
    public CurrencyAccountNotFoundException(Long accountId, Long currencyId) {
        super(String.format("Currency account for account with id %d and currency with id %d could not have been found", accountId, currencyId));
    }
}
