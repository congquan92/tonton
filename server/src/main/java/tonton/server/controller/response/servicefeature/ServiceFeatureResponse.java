package tonton.server.controller.response.servicefeature;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ServiceFeatureResponse {
    private Long id;
    private String name;
    private String slug;
    private String iconUrl;
    private String shortDescription;
    private String content;
    private Integer sortOrder;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
