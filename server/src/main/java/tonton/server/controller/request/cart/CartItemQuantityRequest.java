package tonton.server.controller.request.cart;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CartItemQuantityRequest {
    @NotNull
    @DecimalMin(value = "0.00")
    private BigDecimal quantity;
}
