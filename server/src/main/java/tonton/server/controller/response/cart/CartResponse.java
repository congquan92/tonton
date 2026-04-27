package tonton.server.controller.response.cart;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
public class CartResponse {
    private Long id;
    private Long userId;
    private BigDecimal totalQuantity;
    private BigDecimal subtotal;
    private BigDecimal vatAmount;
    private BigDecimal shippingFee;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private List<CartItemResponse> items;
}
