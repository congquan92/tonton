package tonton.server.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import tonton.server.exception.UnauthorizedException;
import tonton.server.model.User;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_USER_ID = "uid";
    private static final String CLAIM_TOKEN_VERSION = "tv";
    private static final String CLAIM_TOKEN_TYPE = "typ";

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        return buildToken(user, TokenType.ACCESS, jwtProperties.getAccessTokenExpirationMs());
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, TokenType.REFRESH, jwtProperties.getRefreshTokenExpirationMs());
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException ex) {
            throw new UnauthorizedException("Token không hợp lệ hoặc đã hết hạn");
        }
    }

    public String extractUsername(String token) {
        return parseClaims(token).getSubject();
    }

    public Integer extractTokenVersion(String token) {
        return parseClaims(token).get(CLAIM_TOKEN_VERSION, Integer.class);
    }

    public TokenType extractTokenType(String token) {
        String tokenType = parseClaims(token).get(CLAIM_TOKEN_TYPE, String.class);
        return TokenType.valueOf(tokenType);
    }

    public boolean isTokenValid(String token, TokenType expectedTokenType, User user) {
        try {
            Claims claims = parseClaims(token);
            String username = claims.getSubject();
            Integer tokenVersion = claims.get(CLAIM_TOKEN_VERSION, Integer.class);
            String type = claims.get(CLAIM_TOKEN_TYPE, String.class);

            return username != null
                    && username.equals(user.getUsername())
                    && tokenVersion != null
                    && tokenVersion.equals(normalizeTokenVersion(user.getTokenVersion()))
                    && expectedTokenType.name().equals(type)
                    && claims.getExpiration().after(new Date());
        } catch (Exception ex) {
            return false;
        }
    }

    private String buildToken(User user, TokenType tokenType, Long expirationMs) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject(user.getUsername())
                .claim(CLAIM_USER_ID, user.getId())
                .claim(CLAIM_ROLE, user.getRole().getName().name())
                .claim(CLAIM_TOKEN_VERSION, normalizeTokenVersion(user.getTokenVersion()))
                .claim(CLAIM_TOKEN_TYPE, tokenType.name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(signingKey)
                .compact();
    }

    private int normalizeTokenVersion(Integer tokenVersion) {
        return tokenVersion == null ? 1 : tokenVersion;
    }
}
