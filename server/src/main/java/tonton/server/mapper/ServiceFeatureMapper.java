package tonton.server.mapper;

import tonton.server.controller.request.servicefeature.ServiceFeatureRequest;
import tonton.server.controller.response.servicefeature.ServiceFeatureResponse;
import tonton.server.model.ServiceFeature;

public final class ServiceFeatureMapper {
    private ServiceFeatureMapper() {
    }

    public static ServiceFeature toEntity(ServiceFeatureRequest request) {
        ServiceFeature entity = new ServiceFeature();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(ServiceFeature entity, ServiceFeatureRequest request) {
        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        entity.setIconUrl(request.getIconUrl());
        entity.setShortDescription(request.getShortDescription());
        entity.setContent(request.getContent());
        entity.setSortOrder(request.getSortOrder());
        entity.setIsActive(request.getIsActive());
    }

    public static ServiceFeatureResponse toResponse(ServiceFeature entity) {
        return ServiceFeatureResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .iconUrl(entity.getIconUrl())
                .shortDescription(entity.getShortDescription())
                .content(entity.getContent())
                .sortOrder(entity.getSortOrder())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
