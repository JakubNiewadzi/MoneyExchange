package pl.niewadzj.moneyExchange.api.account.records;

import lombok.Builder;

@Builder
public record AccountResponse(Long id,
                              String accountNumber) {
}
