package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponse {
    private Long id;
    private Long parentId;
    private String name;
    private String slug;
    private Boolean isActive;
}