package pl.niewadzj.moneyExchange.exceptions.account;

import pl.niewadzj.moneyExchange.exceptions.BadRequestException;

public class NotEnoughMoneyException extends BadRequestException {
    public NotEnoughMoneyException() {
        super("You do not have enough money on your account to perform this action");
    }
}
