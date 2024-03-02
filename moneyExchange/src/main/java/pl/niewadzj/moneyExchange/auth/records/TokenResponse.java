package pl.niewadzj.moneyExchange.auth.records;

import lombok.Builder;

@Builder
public record TokenResponse(String authToken, String refreshToken)
{}
