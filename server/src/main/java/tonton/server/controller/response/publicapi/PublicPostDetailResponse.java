package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PublicPostDetailResponse {
    private Long id;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private String summary;
    private String content;
    private Long categoryId;
    private String categoryName;
    private String authorName;
    private Integer viewCount;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PublicPostCardResponse> relatedPosts;
}
