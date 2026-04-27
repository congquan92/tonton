package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PublicPostCardResponse {
    private Long id;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private String summary;
    private Long categoryId;
    private String categoryName;
    private Integer viewCount;
    private LocalDateTime publishedAt;
}
