package tonton.server.config.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.auth.cookie")
public class AuthCookieProperties {
    private String refreshTokenName = "refresh_token";
    private String path = "/";
    private String domain;
    private String sameSite = "Lax";
    private boolean secure = false;
}
