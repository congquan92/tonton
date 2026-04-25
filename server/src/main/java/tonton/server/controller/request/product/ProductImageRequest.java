package tonton.server.controller.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductImageRequest {
    @NotNull
    private Long productId;

    @NotBlank
    private String imageUrl;

    private Boolean isPrimary;

    private Integer sortOrder;
}