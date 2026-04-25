package tonton.server.controller.response.quote;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class QuoteItemResponse {
    private Long id;
    private Long quoteId;
    private Long productId;
    private Long uomId;
    private BigDecimal quantity;
    private BigDecimal quotedPrice;
}