package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PriceTierResponse {
    private Long id;
    private Long productId;
    private Long roleId;
    private BigDecimal minQty;
    private BigDecimal price;
}