package pl.niewadzj.moneyExchange.exceptions.currency;

import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class CurrencyNotFoundException extends NotFoundException {
    public CurrencyNotFoundException(Long id) {
        super(String.format("Currency with id %d could not have been found", id));
    }
}
