package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PublicServiceFeatureResponse {
    private Long id;
    private String name;
    private String slug;
    private String iconUrl;
    private String shortDescription;
    private String content;
    private Integer sortOrder;
}
