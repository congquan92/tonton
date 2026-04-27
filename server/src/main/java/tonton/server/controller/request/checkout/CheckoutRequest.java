package tonton.server.controller.request.checkout;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.CheckoutShippingMethod;
import tonton.server.common.enums.PaymentMethod;

@Getter
@Setter
public class CheckoutRequest {
    private Long shippingAddressId;

    private String shippingAddressSnapshot;

    @NotNull
    private CheckoutShippingMethod shippingMethod;

    @NotNull
    private PaymentMethod paymentMethod;
}
