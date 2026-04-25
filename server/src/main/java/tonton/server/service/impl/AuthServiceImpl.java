package tonton.server.service.impl;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.common.enums.RoleName;
import tonton.server.config.security.jwt.JwtProperties;
import tonton.server.config.security.jwt.JwtService;
import tonton.server.config.security.jwt.TokenType;
import tonton.server.controller.request.auth.AuthLoginRequest;
import tonton.server.controller.request.auth.AuthRegisterRequest;
import tonton.server.controller.request.auth.RefreshTokenRequest;
import tonton.server.controller.response.auth.AuthResponse;
import tonton.server.controller.response.auth.AuthUserResponse;
import tonton.server.exception.BadRequestException;
import tonton.server.exception.ConflictException;
import tonton.server.exception.NotFoundException;
import tonton.server.exception.UnauthorizedException;
import tonton.server.model.Role;
import tonton.server.model.User;
import tonton.server.repository.RoleRepository;
import tonton.server.repository.UserRepository;
import tonton.server.service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public AuthResponse register(AuthRegisterRequest request) {
        validateRegisterRequest(request);

        Role defaultRole = roleRepository.findByName(RoleName.B2C)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy vai trò mặc định B2C"));

        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setFullName(request.getFullName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(defaultRole);
        user.setTokenVersion(1);

        User savedUser = userRepository.save(user);
        return buildAuthResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(AuthLoginRequest request) {
        String usernameOrEmail = request.getUsernameOrEmail().trim();
        User user = userRepository.findByUsername(usernameOrEmail)
                .or(() -> userRepository.findByEmail(usernameOrEmail))
                .orElseThrow(() -> new UnauthorizedException("Tên đăng nhập/email hoặc mật khẩu không đúng"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Tên đăng nhập/email hoặc mật khẩu không đúng");
        }

        return buildAuthResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse refresh(RefreshTokenRequest request) {
        String refreshToken = stripBearerPrefix(request.getRefreshToken());
        Claims claims = jwtService.parseClaims(refreshToken);
        String username = claims.getSubject();
        if (username == null || username.isBlank()) {
            throw new UnauthorizedException("Refresh token không hợp lệ");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Refresh token không hợp lệ"));

        if (!jwtService.isTokenValid(refreshToken, TokenType.REFRESH, user)) {
            throw new UnauthorizedException("Refresh token không hợp lệ");
        }

        return buildAuthResponse(user);
    }

    @Override
    @Transactional
    public void logout(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Không tìm thấy người dùng"));

        int tokenVersion = user.getTokenVersion() == null ? 1 : user.getTokenVersion();
        user.setTokenVersion(tokenVersion + 1);
        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthUserResponse me(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Không tìm thấy người dùng"));
        return toAuthUserResponse(user);
    }

    private void validateRegisterRequest(AuthRegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ConflictException("Tên đăng nhập đã tồn tại");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email đã tồn tại");
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new ConflictException("Số điện thoại đã tồn tại");
        }
        if (request.getPassword() == null || request.getPassword().length() < 6) {
            throw new BadRequestException("Mật khẩu phải có ít nhất 6 ký tự");
        }
    }

    private AuthResponse buildAuthResponse(User user) {
        return AuthResponse.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .tokenType("Bearer")
                .accessTokenExpiresInMs(jwtProperties.getAccessTokenExpirationMs())
                .refreshTokenExpiresInMs(jwtProperties.getRefreshTokenExpirationMs())
                .user(toAuthUserResponse(user))
                .build();
    }

    private AuthUserResponse toAuthUserResponse(User user) {
        if (user.getRole() == null || user.getRole().getName() == null) {
            throw new BadRequestException("Vai trò người dùng chưa được cấu hình");
        }
        return AuthUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }

    private String stripBearerPrefix(String token) {
        if (token == null) {
            return null;
        }
        String trimmed = token.trim();
        if (trimmed.regionMatches(true, 0, "Bearer ", 0, 7)) {
            return trimmed.substring(7).trim();
        }
        return trimmed;
    }
}
