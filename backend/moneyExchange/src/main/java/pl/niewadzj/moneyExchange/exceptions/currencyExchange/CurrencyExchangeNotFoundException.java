package pl.niewadzj.moneyExchange.exceptions.currencyExchange;

import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class CurrencyExchangeNotFoundException extends NotFoundException {
    public CurrencyExchangeNotFoundException(Long id) {
        super(String.format("Currency exchange with id %d could not have been found", id));
    }
}
