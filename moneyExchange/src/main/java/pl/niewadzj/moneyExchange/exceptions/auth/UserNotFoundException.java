package pl.niewadzj.moneyExchange.exceptions.auth;

import pl.niewadzj.moneyExchange.exceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String email) {
        super(String.format("User with email %s does not exist", email));
    }
}
