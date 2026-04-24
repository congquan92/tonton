package tonton.server.mapper;

import tonton.server.model.Category;
import tonton.server.controller.request.CategoryRequest;
import tonton.server.controller.response.CategoryResponse;

public final class CategoryMapper {
    private CategoryMapper() {
    }

    public static Category toEntity(CategoryRequest request) {
        Category entity = new Category();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Category entity, CategoryRequest request) {
        entity.setParent(categoryRef(request.getParentId()));
        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        entity.setIsActive(request.getIsActive());
    }

    public static CategoryResponse toResponse(Category entity) {
        return CategoryResponse.builder()
                .id(entity.getId())
                .parentId(entity.getParent() != null ? entity.getParent().getId() : null)
                .name(entity.getName())
                .slug(entity.getSlug())
                .isActive(entity.getIsActive())
                .build();
    }

    private static Category categoryRef(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}