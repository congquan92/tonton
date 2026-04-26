package tonton.server.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tonton.server.config.security.AppUserPrincipal;
import tonton.server.config.security.jwt.AuthCookieService;
import tonton.server.controller.request.auth.AuthLoginRequest;
import tonton.server.controller.request.auth.AuthRegisterRequest;
import tonton.server.controller.response.auth.AuthResponse;
import tonton.server.controller.response.auth.AuthUserResponse;
import tonton.server.exception.UnauthorizedException;
import tonton.server.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthCookieService authCookieService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody AuthRegisterRequest request,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.register(request);
        authCookieService.writeRefreshCookie(response, authResponse.getRefreshToken());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody AuthLoginRequest request,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.login(request);
        authCookieService.writeRefreshCookie(response, authResponse.getRefreshToken());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshToken = authCookieService.extractRefreshToken(request);
        if (refreshToken == null || refreshToken.isBlank()) {
            throw new UnauthorizedException("Phiên đăng nhập đã hết hạn hoặc không hợp lệ");
        }

        AuthResponse authResponse = authService.refresh(refreshToken);
        authCookieService.writeRefreshCookie(response, authResponse.getRefreshToken());
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(Authentication authentication, HttpServletResponse response) {
        authCookieService.clearRefreshCookie(response);
        authService.logout(extractUsername(authentication));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<AuthUserResponse> me(Authentication authentication) {
        return ResponseEntity.ok(authService.me(extractUsername(authentication)));
    }

    private String extractUsername(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof AppUserPrincipal principal)) {
            throw new UnauthorizedException("Bạn chưa đăng nhập vào hệ thống");
        }
        return principal.getUsername();
    }
}
