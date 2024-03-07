package pl.niewadzj.moneyExchange.exceptions.account;

import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException(String email) {
        super(String.format("Account for user email %s could not have been found", email));
    }
}
