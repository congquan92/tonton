package tonton.server.mapper;

import tonton.server.controller.request.project.ProjectRequest;
import tonton.server.controller.response.project.ProjectResponse;
import tonton.server.model.Project;

public final class ProjectMapper {
    private ProjectMapper() {
    }

    public static Project toEntity(ProjectRequest request) {
        Project entity = new Project();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Project entity, ProjectRequest request) {
        entity.setTitle(request.getTitle());
        entity.setSlug(request.getSlug());
        entity.setThumbnailUrl(request.getThumbnailUrl());
        entity.setSummary(request.getSummary());
        entity.setContent(request.getContent());
        entity.setCategory(request.getCategory());
        entity.setLocation(request.getLocation());
        entity.setCompletedAt(request.getCompletedAt());
        entity.setFeatured(request.getFeatured());
        entity.setIsActive(request.getIsActive());
    }

    public static ProjectResponse toResponse(Project entity) {
        return ProjectResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .slug(entity.getSlug())
                .thumbnailUrl(entity.getThumbnailUrl())
                .summary(entity.getSummary())
                .content(entity.getContent())
                .category(entity.getCategory())
                .location(entity.getLocation())
                .completedAt(entity.getCompletedAt())
                .featured(entity.getFeatured())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
