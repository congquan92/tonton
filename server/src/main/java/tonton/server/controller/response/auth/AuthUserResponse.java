package tonton.server.controller.response.auth;

import lombok.Builder;
import lombok.Getter;
import tonton.server.common.enums.RoleName;

@Getter
@Builder
public class AuthUserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private RoleName role;
}
