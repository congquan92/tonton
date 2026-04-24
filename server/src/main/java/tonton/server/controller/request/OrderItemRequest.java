package tonton.server.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemRequest {
    @NotNull
    private Long orderId;

    @NotNull
    private Long productId;

    @NotNull
    private Long uomId;

    @NotNull
    private BigDecimal quantity;

    @NotNull
    private BigDecimal price;
}