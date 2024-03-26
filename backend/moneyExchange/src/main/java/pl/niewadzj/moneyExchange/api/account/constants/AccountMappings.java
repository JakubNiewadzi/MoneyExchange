package pl.niewadzj.moneyExchange.api.account.constants;

public final class AccountMappings {

    private AccountMappings() {
    }

    public static final String ACCOUNT_MAPPING = "/api/v1/account";
    public static final String DEPOSIT_MAPPING = "/deposit";
    public static final String WITHDRAW_MAPPING = "/withdraw";
    public static final String GET_CURRENCY_ACCOUNTS = "/getAccounts";
    public static final String GET_CURRENCY_ACCOUNT = "/getAccount";
    public static final String GET_ACTIVE_ACCOUNTS = "/getFilteredAccounts";
    public static final String SUSPEND_ACCOUNT_MAPPING = "/suspendAccount";
    public static final String ACTIVATE_SUSPENDED_ACCOUNT_MAPPING = "activateSuspendedAccount";
}
