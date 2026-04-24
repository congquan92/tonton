package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;
import tonton.server.common.enums.OrderStatus;
import tonton.server.common.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class OrderResponse {
    private Long id;
    private Long quoteId;
    private Long userId;
    private String userFullName;
    private BigDecimal totalAmount;
    private BigDecimal shippingFee;
    private Long shippingAddressId;
    private String shippingAddressSnapshot;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}