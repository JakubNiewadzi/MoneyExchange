package pl.niewadzj.moneyExchange.api.transfer.constants;

public final class TransferConstants {

    public static final String PAGE_NUMBER = "0";
    public static final String MINIMAL_AMOUNT = "0.0";
    public static final String PAGE_SIZE = "10";
    public static final String ID_NULL_MSG = "Id cannot be a null value";
    public static final String AMOUNT_NULL = "Amount cannot be a null value";
    public static final String ACCOUNT_NUMBER_NULL = "Account number cannot be a null value";
    public static final String ACCOUNT_NUMBER_REGEX = "\\d{26}";
    public static final int AMOUNT_INTEGER = 12;
    public static final int AMOUNT_FRACTION = 2;

    private TransferConstants() {
    }
}
