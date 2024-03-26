package pl.niewadzj.moneyExchange.api.auth.records;

import lombok.Builder;

@Builder
public record TokenResponse(String authToken, String refreshToken) {
}
