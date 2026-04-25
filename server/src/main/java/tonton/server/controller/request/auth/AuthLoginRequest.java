package tonton.server.controller.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthLoginRequest {
    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
