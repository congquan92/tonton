package tonton.server.controller.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ProductRequest {
    @NotBlank
    @Size(max = 50)
    private String sku;

    private Long categoryId;

    @NotBlank
    private String name;

    @NotNull
    private Long baseUomId;

    private BigDecimal quantity;

    @NotNull
    private Map<String, Object> attributes;

    private Boolean isActive;
}