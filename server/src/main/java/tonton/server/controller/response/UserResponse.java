package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;
import tonton.server.common.enums.RoleName;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String phoneNumber;
    private String fullName;
    private LocalDate dateOfBirth;
    private String email;
    private String username;
    private Long roleId;
    private RoleName roleName;
    private Integer tokenVersion;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}