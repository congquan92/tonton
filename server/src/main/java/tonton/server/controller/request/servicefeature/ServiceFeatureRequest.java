package tonton.server.controller.request.servicefeature;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceFeatureRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    private String iconUrl;

    private String shortDescription;

    private String content;

    private Integer sortOrder;

    private Boolean isActive;
}
