package tonton.server.mapper;

import tonton.server.model.Category;
import tonton.server.model.Product;
import tonton.server.model.Uom;
import tonton.server.controller.request.ProductRequest;
import tonton.server.controller.response.ProductResponse;

public final class ProductMapper {
    private ProductMapper() {
    }

    public static Product toEntity(ProductRequest request) {
        Product entity = new Product();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(Product entity, ProductRequest request) {
        entity.setSku(request.getSku());
        entity.setCategory(categoryRef(request.getCategoryId()));
        entity.setName(request.getName());
        entity.setBaseUom(uomRef(request.getBaseUomId()));
        entity.setQuantity(request.getQuantity());
        entity.setAttributes(request.getAttributes());
        entity.setIsActive(request.getIsActive());
    }

    public static ProductResponse toResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .sku(entity.getSku())
                .categoryId(entity.getCategory() != null ? entity.getCategory().getId() : null)
                .categoryName(entity.getCategory() != null ? entity.getCategory().getName() : null)
                .name(entity.getName())
                .baseUomId(entity.getBaseUom() != null ? entity.getBaseUom().getId() : null)
                .baseUomName(entity.getBaseUom() != null ? entity.getBaseUom().getName() : null)
                .quantity(entity.getQuantity())
                .attributes(entity.getAttributes())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
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

    private static Uom uomRef(Long id) {
        Uom uom = new Uom();
        uom.setId(id);
        return uom;
    }
}