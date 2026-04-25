package tonton.server.controller.response.uom;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class UomConversionResponse {
    private Long id;
    private Long productId;
    private Long fromUomId;
    private Long toUomId;
    private BigDecimal conversionRate;
}