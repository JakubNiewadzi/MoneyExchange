package pl.niewadzj.moneyExchange.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final long EXPIRATION_TIME = 1000L * 60L * 10L;
    private static final long REFRESH_TIME = 1000L * 60L * 60L;

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateRefreshToken(UserDetails user) {
        return generateToken(Map.of("role", user.getAuthorities()), user, REFRESH_TIME);
    }

    public String generateToken(UserDetails user) {
        return generateToken(Map.of("role", user.getAuthorities()), user, EXPIRATION_TIME);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails user, long expiration) {
        final Date now = new Date(System.currentTimeMillis());
        final Date expirationDate = new Date(System.currentTimeMillis() + expiration);

        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(user.getUsername())
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        final String tokenEmail = extractEmail(token);
        return tokenEmail.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
