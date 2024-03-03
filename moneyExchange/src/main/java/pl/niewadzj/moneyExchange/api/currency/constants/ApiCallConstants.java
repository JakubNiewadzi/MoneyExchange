package pl.niewadzj.moneyExchange.api.currency.constants;

public final class ApiCallConstants {
    private ApiCallConstants(){}

    public static final Long SCHEDULE_RATE = 1000L * 60L;
    public static final String SUPPLIER_URL = "https://api.nbp.pl/api/exchangerates/tables/a?format=json";
}
