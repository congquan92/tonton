package tonton.server.config.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthCookieService {

    private final AuthCookieProperties authCookieProperties;
    private final JwtProperties jwtProperties;

    public void writeRefreshCookie(HttpServletResponse response, String refreshToken) {
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                buildCookie(
                        authCookieProperties.getRefreshTokenName(),
                        refreshToken,
                        jwtProperties.getRefreshTokenExpirationMs()
                ).toString()
        );
    }

    public void clearRefreshCookie(HttpServletResponse response) {
        response.addHeader(
                HttpHeaders.SET_COOKIE,
                buildCookie(authCookieProperties.getRefreshTokenName(), "", 0L).toString()
        );
    }

    public String extractRefreshToken(HttpServletRequest request) {
        return extractCookieValue(request, authCookieProperties.getRefreshTokenName());
    }

    private String extractCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }

        Optional<String> token = Arrays.stream(cookies)
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .map(Cookie::getValue)
                .findFirst();
        return token.filter(StringUtils::hasText).orElse(null);
    }

    private ResponseCookie buildCookie(String name, String value, Long maxAgeMs) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, value == null ? "" : value)
                .httpOnly(true)
                .secure(authCookieProperties.isSecure())
                .path(authCookieProperties.getPath())
                .sameSite(authCookieProperties.getSameSite())
                .maxAge(Duration.ofMillis(maxAgeMs == null ? 0L : maxAgeMs));

        if (StringUtils.hasText(authCookieProperties.getDomain())) {
            builder.domain(authCookieProperties.getDomain());
        }

        return builder.build();
    }
}
