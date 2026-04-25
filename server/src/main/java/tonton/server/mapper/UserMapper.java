package tonton.server.mapper;

import tonton.server.model.Role;
import tonton.server.model.User;
import tonton.server.controller.request.user.UserRequest;
import tonton.server.controller.response.user.UserResponse;

public final class UserMapper {
    private UserMapper() {
    }

    public static User toEntity(UserRequest request) {
        User entity = new User();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(User entity, UserRequest request) {
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setFullName(request.getFullName());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setEmail(request.getEmail());
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        entity.setTokenVersion(request.getTokenVersion());
        entity.setRole(roleRef(request.getRoleId()));
    }

    public static UserResponse toResponse(User entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .phoneNumber(entity.getPhoneNumber())
                .fullName(entity.getFullName())
                .dateOfBirth(entity.getDateOfBirth())
                .email(entity.getEmail())
                .username(entity.getUsername())
                .roleId(entity.getRole() != null ? entity.getRole().getId() : null)
                .roleName(entity.getRole() != null ? entity.getRole().getName() : null)
                .tokenVersion(entity.getTokenVersion())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private static Role roleRef(Long id) {
        if (id == null) {
            return null;
        }
        Role role = new Role();
        role.setId(id);
        return role;
    }
}