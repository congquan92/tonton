package tonton.server.controller.response.product;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Builder
public class ProductResponse {
    private Long id;
    private String sku;
    private Long categoryId;
    private String categoryName;
    private String name;
    private Long baseUomId;
    private String baseUomName;
    private BigDecimal quantity;
    private Map<String, Object> attributes;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}