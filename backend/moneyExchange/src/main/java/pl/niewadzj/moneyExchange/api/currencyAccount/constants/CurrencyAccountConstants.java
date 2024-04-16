package pl.niewadzj.moneyExchange.api.currencyAccount.constants;

public final class CurrencyAccountConstants {

    public static final String INCORRECT_AMOUNT_MSG = "Top up amount must have at most two decimal places";
    public static final String NEGATIVE_AMOUNT_MSG = "Top up amount must be positive";
    public static final String ID_NULL_MSG = "Id cannot be a null value";
    public static final String MINIMAL_AMOUNT = "0.0";
    public static final int AMOUNT_INTEGER = 12;
    public static final int AMOUNT_FRACTION = 2;

    private CurrencyAccountConstants() {
    }
}
