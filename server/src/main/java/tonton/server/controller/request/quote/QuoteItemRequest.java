package tonton.server.controller.request.quote;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class QuoteItemRequest {
    @NotNull
    private Long quoteId;

    @NotNull
    private Long productId;

    @NotNull
    private Long uomId;

    @NotNull
    private BigDecimal quantity;

    private BigDecimal quotedPrice;
}