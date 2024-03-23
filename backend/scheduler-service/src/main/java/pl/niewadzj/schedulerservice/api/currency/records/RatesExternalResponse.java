package pl.niewadzj.schedulerservice.api.currency.records;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record RatesExternalResponse(String table,
                                    String no,
                                    Date effectiveDate,
                                    List<CurrencyExternalResponse> rates) {
}
