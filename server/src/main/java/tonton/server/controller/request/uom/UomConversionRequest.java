package tonton.server.controller.request.uom;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UomConversionRequest {
    @NotNull
    private Long productId;

    @NotNull
    private Long fromUomId;

    @NotNull
    private Long toUomId;

    @NotNull
    private BigDecimal conversionRate;
}