package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class PublicProjectCardResponse {
    private Long id;
    private String title;
    private String slug;
    private String thumbnailUrl;
    private String summary;
    private String category;
    private String location;
    private LocalDate completedAt;
}
