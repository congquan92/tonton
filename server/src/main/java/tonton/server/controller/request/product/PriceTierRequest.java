package tonton.server.controller.request.product;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceTierRequest {
    @NotNull
    private Long productId;

    @NotNull
    private Long roleId;

    @NotNull
    private BigDecimal minQty;

    @NotNull
    private BigDecimal price;
}