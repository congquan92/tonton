package tonton.server.controller.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import tonton.server.common.enums.PostStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    private String thumbnailUrl;

    private String summary;

    @NotBlank
    private String content;

    private Long categoryId;

    private Long authorId;

    private PostStatus status;

    private Integer viewCount;

    private LocalDateTime publishedAt;
}