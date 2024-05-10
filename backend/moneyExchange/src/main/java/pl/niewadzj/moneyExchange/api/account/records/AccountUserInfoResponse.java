package pl.niewadzj.moneyExchange.api.account.records;

import lombok.Builder;

@Builder
public record AccountUserInfoResponse(Long id,
                                      String accountNumber,
                                      String email,
                                      String firstName,
                                      String lastName) {
}
