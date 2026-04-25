package tonton.server.mapper;

import tonton.server.model.Role;
import tonton.server.controller.request.role.RoleRequest;
import tonton.server.controller.response.role.RoleResponse;

public final class RoleMapper {
    private RoleMapper() {
    }

    public static Role toEntity(RoleRequest request) {
        Role entity = new Role();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Role entity, RoleRequest request) {
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
    }

    public static RoleResponse toResponse(Role entity) {
        return RoleResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}