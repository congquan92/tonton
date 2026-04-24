package tonton.server.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.RoleName;

@Getter
@Setter
public class RoleRequest {
    @NotNull
    private RoleName name;

    @Size(max = 255)
    private String description;
}