package tonton.server.mapper;

import tonton.server.model.PostCategory;
import tonton.server.controller.request.post.PostCategoryRequest;
import tonton.server.controller.response.post.PostCategoryResponse;

public final class PostCategoryMapper {
    private PostCategoryMapper() {
    }

    public static PostCategory toEntity(PostCategoryRequest request) {
        PostCategory entity = new PostCategory();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(PostCategory entity, PostCategoryRequest request) {
        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        entity.setIsActive(request.getIsActive());
    }

    public static PostCategoryResponse toResponse(PostCategory entity) {
        return PostCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .slug(entity.getSlug())
                .isActive(entity.getIsActive())
                .build();
    }
}