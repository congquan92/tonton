package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PublicPriceTierResponse {
    private BigDecimal minQty;
    private BigDecimal price;
    private String role;
}
