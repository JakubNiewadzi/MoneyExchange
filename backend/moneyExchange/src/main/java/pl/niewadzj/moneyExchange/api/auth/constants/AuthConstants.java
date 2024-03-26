package pl.niewadzj.moneyExchange.api.auth.constants;

public final class AuthConstants {

    private AuthConstants() {
    }


    public static final String STRING_NOT_NULL = "Given string cannot be null";
    public static final String STRING_NOT_BLANK = "Given string cannot be blank";
    public static final String STRING_SIZE = "Given string must be between 3 and 20 characters long";
    public static final String PASSWORD_SIZE = "Password must be between 8 and 30 characters long";
    public static final String EMAIL_MESSAGE = "Given string must be a valid email";
    public static final int MIN_NAME = 3;
    public static final int MAX_NAME = 20;
    public static final int MIN_PASSWORD = 8;
    public static final int MAX_PASSWORD = 30;

}
