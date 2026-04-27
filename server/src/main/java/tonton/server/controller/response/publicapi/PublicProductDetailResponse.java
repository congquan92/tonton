package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class PublicProductDetailResponse {
    private Long id;
    private String sku;
    private String name;
    private Long categoryId;
    private String categoryName;
    private Long baseUomId;
    private String baseUomName;
    private String baseUomSymbol;
    private BigDecimal quantity;
    private Map<String, Object> attributes;
    private String primaryImageUrl;
    private List<String> imageUrls;
    private List<PublicPriceTierResponse> priceTiers;
    private BigDecimal displayPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
