package pl.niewadzj.moneyExchange.exceptions.auth;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class PasswordsDoNotMatchException extends BadRequestException {

    public PasswordsDoNotMatchException() {
        super("Given password does not match");
    }
}
