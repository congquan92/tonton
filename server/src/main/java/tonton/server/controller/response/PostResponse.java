package tonton.server.controller.response;

import lombok.Builder;
import lombok.Getter;
import tonton.server.common.enums.PostStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private String summary;
    private String content;
    private Long categoryId;
    private String categoryName;
    private Long authorId;
    private String authorName;
    private PostStatus status;
    private Integer viewCount;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}