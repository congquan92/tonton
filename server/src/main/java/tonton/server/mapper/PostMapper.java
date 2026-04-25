package tonton.server.mapper;

import tonton.server.model.Post;
import tonton.server.model.PostCategory;
import tonton.server.model.User;
import tonton.server.controller.request.post.PostRequest;
import tonton.server.controller.response.post.PostResponse;

public final class PostMapper {
    private PostMapper() {
    }

    public static Post toEntity(PostRequest request) {
        Post entity = new Post();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Post entity, PostRequest request) {
        entity.setTitle(request.getTitle());
        entity.setSlug(request.getSlug());
        entity.setThumbnailUrl(request.getThumbnailUrl());
        entity.setSummary(request.getSummary());
        entity.setContent(request.getContent());
        entity.setCategory(categoryRef(request.getCategoryId()));
        entity.setAuthor(authorRef(request.getAuthorId()));
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        entity.setViewCount(request.getViewCount());
        entity.setPublishedAt(request.getPublishedAt());
    }

    public static PostResponse toResponse(Post entity) {
        return PostResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .slug(entity.getSlug())
                .thumbnailUrl(entity.getThumbnailUrl())
                .summary(entity.getSummary())
                .content(entity.getContent())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .authorId(entity.getAuthor() != null ? entity.getAuthor().getId() : null)
                .authorName(entity.getAuthor() != null ? entity.getAuthor().getFullName() : null)
                .status(entity.getStatus())
                .viewCount(entity.getViewCount())
                .publishedAt(entity.getPublishedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private static PostCategory categoryRef(Long id) {
        if (id == null) {
            return null;
        }
        PostCategory category = new PostCategory();
        category.setId(id);
        return category;
    }

    private static User authorRef(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}