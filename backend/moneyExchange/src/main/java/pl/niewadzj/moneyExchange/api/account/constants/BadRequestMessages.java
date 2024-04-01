package pl.niewadzj.moneyExchange.api.account.constants;

public final class BadRequestMessages {

    public static final String INCORRECT_AMOUNT_MSG = "Top up amount must have at most two decimal places";
    public static final String NEGATIVE_AMOUNT_MSG = "Top up amount must be positive";
    private BadRequestMessages() {
    }
}
