package pl.niewadzj.moneyExchange.exceptions.auth;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class UserAlreadyExistsException extends BadRequestException {

    public UserAlreadyExistsException(String email) {
        super(String.format("User with email %s already exits", email));
    }

}
