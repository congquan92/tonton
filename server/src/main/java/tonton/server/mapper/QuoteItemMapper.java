package tonton.server.mapper;

import tonton.server.model.Product;
import tonton.server.model.Quote;
import tonton.server.model.QuoteItem;
import tonton.server.model.Uom;
import tonton.server.controller.request.QuoteItemRequest;
import tonton.server.controller.response.QuoteItemResponse;

public final class QuoteItemMapper {
    private QuoteItemMapper() {
    }

    public static QuoteItem toEntity(QuoteItemRequest request) {
        QuoteItem entity = new QuoteItem();
        updateEntity(entity, request);
        return entity;
    }

    public static void updateEntity(QuoteItem entity, QuoteItemRequest request) {
        entity.setQuote(quoteRef(request.getQuoteId()));
        entity.setProduct(productRef(request.getProductId()));
        entity.setUom(uomRef(request.getUomId()));
        entity.setQuantity(request.getQuantity());
        entity.setQuotedPrice(request.getQuotedPrice());
    }

    public static QuoteItemResponse toResponse(QuoteItem entity) {
        return QuoteItemResponse.builder()
                .id(entity.getId())
                .quoteId(entity.getQuote() != null ? entity.getQuote().getId() : null)
                .productId(entity.getProduct() != null ? entity.getProduct().getId() : null)
                .uomId(entity.getUom() != null ? entity.getUom().getId() : null)
                .quantity(entity.getQuantity())
                .quotedPrice(entity.getQuotedPrice())
                .build();
    }

    private static Quote quoteRef(Long id) {
        Quote quote = new Quote();
        quote.setId(id);
        return quote;
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