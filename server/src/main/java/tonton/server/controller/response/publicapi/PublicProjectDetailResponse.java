package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PublicProjectDetailResponse {
    private Long id;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private String summary;
    private String content;
    private String category;
    private String location;
    private LocalDate completedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
