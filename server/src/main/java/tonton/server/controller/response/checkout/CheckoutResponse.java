package tonton.server.controller.response.checkout;

import lombok.Builder;
import lombok.Getter;
import tonton.server.common.enums.CheckoutShippingMethod;
import tonton.server.common.enums.OrderStatus;
import tonton.server.common.enums.PaymentMethod;

import java.math.BigDecimal;

@Getter
@Builder
public class CheckoutResponse {
    private Long orderId;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private CheckoutShippingMethod shippingMethod;
    private BigDecimal subtotal;
    private BigDecimal vatAmount;
    private BigDecimal shippingFee;
    private BigDecimal totalAmount;
    private Integer itemCount;
}
