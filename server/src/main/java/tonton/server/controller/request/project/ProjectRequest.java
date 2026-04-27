package tonton.server.controller.request.project;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String slug;

    private String thumbnailUrl;

    private String summary;

    private String content;

    private String category;

    private String location;

    private LocalDate completedAt;

    private Boolean featured;

    private Boolean isActive;
}
