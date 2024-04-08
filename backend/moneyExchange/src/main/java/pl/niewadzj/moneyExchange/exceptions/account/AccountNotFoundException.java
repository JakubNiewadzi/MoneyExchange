package pl.niewadzj.moneyExchange.exceptions.account;

import pl.niewadzj.moneyExchange.entities.user.User;
import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class AccountNotFoundException extends NotFoundException {
    public AccountNotFoundException(User user) {
        super(String.format("Account for user email %s could not have been found", user.getEmail()));
    }

    public AccountNotFoundException(String accountNumber){
        super(String.format("Account with number %s could not have been found", accountNumber));
    }
}
