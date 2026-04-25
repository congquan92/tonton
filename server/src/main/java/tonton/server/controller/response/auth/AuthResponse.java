package tonton.server.controller.response.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Long accessTokenExpiresInMs;
    private Long refreshTokenExpiresInMs;
    private AuthUserResponse user;
}
