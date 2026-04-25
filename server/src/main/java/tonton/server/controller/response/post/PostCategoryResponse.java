package tonton.server.controller.response.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostCategoryResponse {
    private Long id;
    private String name;
    private String slug;
    private Boolean isActive;
}