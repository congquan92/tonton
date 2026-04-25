package tonton.server.mapper;

import tonton.server.model.PriceTier;
import tonton.server.model.Product;
import tonton.server.model.Role;
import tonton.server.controller.request.product.PriceTierRequest;
import tonton.server.controller.response.product.PriceTierResponse;

public final class PriceTierMapper {
    private PriceTierMapper() {
    }

    public static PriceTier toEntity(PriceTierRequest request) {
        PriceTier entity = new PriceTier();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(PriceTier entity, PriceTierRequest request) {
        entity.setProduct(productRef(request.getProductId()));
        entity.setRole(roleRef(request.getRoleId()));
        entity.setMinQty(request.getMinQty());
        entity.setPrice(request.getPrice());
    }

    public static PriceTierResponse toResponse(PriceTier entity) {
        return PriceTierResponse.builder()
                .id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .roleId(entity.getRole() != null ? entity.getRole().getId() : null)
                .minQty(entity.getMinQty())
                .price(entity.getPrice())
                .build();
    }

    private static Product productRef(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    private static Role roleRef(Long id) {
        Role role = new Role();
        role.setId(id);
        return role;
    }
}