package pl.niewadzj.moneyExchange.exceptions.auth;

public class UserAlreadyExistsException extends IllegalArgumentException{

    public UserAlreadyExistsException(String email){
        super(String.format("User with email %s already exits", email));
    }

}
