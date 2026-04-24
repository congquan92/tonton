package tonton.server.mapper;

import tonton.server.model.Product;
import tonton.server.model.Uom;
import tonton.server.model.UomConversion;
import tonton.server.controller.request.UomConversionRequest;
import tonton.server.controller.response.UomConversionResponse;

public final class UomConversionMapper {
    private UomConversionMapper() {
    }

    public static UomConversion toEntity(UomConversionRequest request) {
        UomConversion entity = new UomConversion();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(UomConversion entity, UomConversionRequest request) {
        entity.setProduct(productRef(request.getProductId()));
        entity.setFromUom(uomRef(request.getFromUomId()));
        entity.setToUom(uomRef(request.getToUomId()));
        entity.setConversionRate(request.getConversionRate());
    }

    public static UomConversionResponse toResponse(UomConversion entity) {
        return UomConversionResponse.builder()
                .id(entity.getId())
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .fromUomId(entity.getFromUom() != null ? entity.getFromUom().getId() : null)
                .toUomId(entity.getToUom() != null ? entity.getToUom().getId() : null)
                .conversionRate(entity.getConversionRate())
                .build();
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