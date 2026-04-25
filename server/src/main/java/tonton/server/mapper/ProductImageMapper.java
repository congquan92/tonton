package tonton.server.mapper;

import tonton.server.model.Product;
import tonton.server.model.ProductImage;
import tonton.server.controller.request.product.ProductImageRequest;
import tonton.server.controller.response.product.ProductImageResponse;

public final class ProductImageMapper {
    private ProductImageMapper() {
    }

    public static ProductImage toEntity(ProductImageRequest request) {
        ProductImage entity = new ProductImage();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(ProductImage entity, ProductImageRequest request) {
        entity.setProduct(productRef(request.getProductId()));
        entity.setImageUrl(request.getImageUrl());
        entity.setIsPrimary(request.getIsPrimary());
        entity.setSortOrder(request.getSortOrder());
    }

    public static ProductImageResponse toResponse(ProductImage entity) {
        return ProductImageResponse.builder()
                .id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .imageUrl(entity.getImageUrl())
                .isPrimary(entity.getIsPrimary())
                .sortOrder(entity.getSortOrder())
                .build();
    }

    private static Product productRef(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }
}