package tonton.server.mapper;

import tonton.server.model.User;
import tonton.server.model.UserAddress;
import tonton.server.controller.request.UserAddressRequest;
import tonton.server.controller.response.UserAddressResponse;

public final class UserAddressMapper {
    private UserAddressMapper() {
    }

    public static UserAddress toEntity(UserAddressRequest request) {
        UserAddress entity = new UserAddress();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(UserAddress entity, UserAddressRequest request) {
        entity.setUser(userRef(request.getUserId()));
        entity.setTitle(request.getTitle());
        entity.setReceiverName(request.getReceiverName());
        entity.setReceiverPhone(request.getReceiverPhone());
        entity.setAddress(request.getAddress());
        entity.setIsDefault(request.getIsDefault());
    }

    public static UserAddressResponse toResponse(UserAddress entity) {
        return UserAddressResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .title(entity.getTitle())
                .receiverName(entity.getReceiverName())
                .receiverPhone(entity.getReceiverPhone())
                .address(entity.getAddress())
                .isDefault(entity.getIsDefault())
                .build();
    }

    private static User userRef(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}