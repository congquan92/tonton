package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HomeStatsResponse {
    private long activeProducts;
    private long featuredProjects;
    private long publishedPosts;
    private long activeServices;
}
