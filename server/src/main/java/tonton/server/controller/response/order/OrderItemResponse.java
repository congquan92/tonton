package tonton.server.controller.response.order;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class OrderItemResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long uomId;
    private BigDecimal quantity;
    private BigDecimal price;
}