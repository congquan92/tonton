package tonton.server.mapper;

import tonton.server.model.Order;
import tonton.server.model.OrderItem;
import tonton.server.model.Product;
import tonton.server.model.Uom;
import tonton.server.controller.request.order.OrderItemRequest;
import tonton.server.controller.response.order.OrderItemResponse;

public final class OrderItemMapper {
    private OrderItemMapper() {
    }

    public static OrderItem toEntity(OrderItemRequest request) {
        OrderItem entity = new OrderItem();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(OrderItem entity, OrderItemRequest request) {
        entity.setOrder(orderRef(request.getOrderId()));
        entity.setProduct(productRef(request.getProductId()));
        entity.setUom(uomRef(request.getUomId()));
        entity.setQuantity(request.getQuantity());
        entity.setPrice(request.getPrice());
    }

    public static OrderItemResponse toResponse(OrderItem entity) {
        return OrderItemResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrder() != null ? entity.getOrder().getId() : null)
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .uomId(entity.getUom() != null ? entity.getUom().getId() : null)
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .build();
    }

    private static Order orderRef(Long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    private static Product productRef(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    private static Uom uomRef(Long id) {
        Uom uom = new Uom();
        uom.setId(id);
        return uom;
    }
}