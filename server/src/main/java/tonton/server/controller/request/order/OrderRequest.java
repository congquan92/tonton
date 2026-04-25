package tonton.server.controller.request.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.OrderStatus;
import tonton.server.common.enums.PaymentMethod;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequest {
    private Long quoteId;

    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal totalAmount;

    private BigDecimal shippingFee;

    private Long shippingAddressId;

    @NotBlank
    private String shippingAddressSnapshot;

    private OrderStatus status;

    private PaymentMethod paymentMethod;
}