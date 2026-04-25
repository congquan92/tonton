package tonton.server.mapper;

import tonton.server.model.Order;
import tonton.server.model.Quote;
import tonton.server.model.User;
import tonton.server.model.UserAddress;
import tonton.server.controller.request.order.OrderRequest;
import tonton.server.controller.response.order.OrderResponse;

public final class OrderMapper {
    private OrderMapper() {
    }

    public static Order toEntity(OrderRequest request) {
        Order entity = new Order();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Order entity, OrderRequest request) {
        entity.setQuote(quoteRef(request.getQuoteId()));
        entity.setUser(userRef(request.getUserId()));
        entity.setTotalAmount(request.getTotalAmount());
        entity.setShippingFee(request.getShippingFee());
        entity.setShippingAddress(addressRef(request.getShippingAddressId()));
        entity.setShippingAddressSnapshot(request.getShippingAddressSnapshot());
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        if (request.getPaymentMethod() != null) {
            entity.setPaymentMethod(request.getPaymentMethod());
        }
    }

    public static OrderResponse toResponse(Order entity) {
        return OrderResponse.builder()
                .id(entity.getId())
                .quoteId(entity.getQuote() != null ? entity.getQuote().getId() : null)
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .userFullName(entity.getUser() != null ? entity.getUser().getFullName() : null)
                .totalAmount(entity.getTotalAmount())
                .shippingFee(entity.getShippingFee())
                .shippingAddressId(entity.getShippingAddress() != null ? entity.getShippingAddress().getId() : null)
                .shippingAddressSnapshot(entity.getShippingAddressSnapshot())
                .status(entity.getStatus())
                .paymentMethod(entity.getPaymentMethod())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    private static Quote quoteRef(Long id) {
        if (id == null) {
            return null;
        }
        Quote quote = new Quote();
        quote.setId(id);
        return quote;
    }

    private static User userRef(Long id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    private static UserAddress addressRef(Long id) {
        if (id == null) {
            return null;
        }
        UserAddress address = new UserAddress();
        address.setId(id);
        return address;
    }
}