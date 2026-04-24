package tonton.server.mapper;

import tonton.server.model.Quote;
import tonton.server.model.User;
import tonton.server.controller.request.QuoteRequest;
import tonton.server.controller.response.QuoteResponse;

public final class QuoteMapper {
    private QuoteMapper() {
    }

    public static Quote toEntity(QuoteRequest request) {
        Quote entity = new Quote();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Quote entity, QuoteRequest request) {
        entity.setUser(userRef(request.getUserId()));
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        entity.setNote(request.getNote());
    }

    public static QuoteResponse toResponse(Quote entity) {
        return QuoteResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .userFullName(entity.getUser() != null ? entity.getUser().getFullName() : null)
                .status(entity.getStatus())
                .note(entity.getNote())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private static User userRef(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}