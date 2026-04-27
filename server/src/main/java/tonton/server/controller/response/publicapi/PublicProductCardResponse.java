package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PublicProductCardResponse {
    private Long id;
    private String sku;
    private String name;
    private Long categoryId;
    private String categoryName;
    private String baseUomSymbol;
    private String primaryImageUrl;
    private BigDecimal displayPrice;
}
