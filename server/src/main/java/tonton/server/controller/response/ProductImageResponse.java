package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductImageResponse {
    private Long id;
    private Long productId;
    private String imageUrl;
    private Boolean isPrimary;
    private Integer sortOrder;
}