package tonton.server.service;

import tonton.server.controller.request.auth.AuthLoginRequest;
import tonton.server.controller.request.auth.AuthRegisterRequest;
import tonton.server.controller.response.auth.AuthResponse;
import tonton.server.controller.response.auth.AuthUserResponse;

public interface AuthService {
    AuthResponse register(AuthRegisterRequest request);

    AuthResponse login(AuthLoginRequest request);

    AuthResponse refresh(String refreshToken);

    void logout(String username);

    AuthUserResponse me(String username);
}
