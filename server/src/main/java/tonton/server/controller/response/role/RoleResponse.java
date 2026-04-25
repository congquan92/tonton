package tonton.server.controller.response.role;

import lombok.Builder;
import lombok.Getter;
import tonton.server.common.enums.RoleName;

@Getter
@Builder
public class RoleResponse {
    private Long id;
    private RoleName name;
    private String description;
}