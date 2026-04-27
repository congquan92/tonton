package tonton.server.controller.request.cart;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemUpsertRequest {
    @NotNull
    private Long productId;

    private Long uomId;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal quantity;
}
