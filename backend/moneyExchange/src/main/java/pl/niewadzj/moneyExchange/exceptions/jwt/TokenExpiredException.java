package pl.niewadzj.moneyExchange.exceptions.jwt;

import pl.niewadzj.moneyExchange.exceptions.UnauthorizedException;

public class TokenExpiredException extends UnauthorizedException {
    public TokenExpiredException() {
        super("Refresh token has expired");
    }
}
