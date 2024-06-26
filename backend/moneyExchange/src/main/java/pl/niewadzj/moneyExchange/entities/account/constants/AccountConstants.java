package pl.niewadzj.moneyExchange.entities.account.constants;

public final class AccountConstants {

    public static final int ACCOUNT_NUMBER_SIZE = 26;
    public static final String NUMBER_NULL_MSG = "Account number cannot be null";
    public static final String ACCOUNT_TABLE_NAME = "accounts";
    public static final String ACCOUNT_MAP_NAME = "account";
    public static final String JOIN_COLUMN_NAME = "user_id";

    private AccountConstants() {
    }

}
