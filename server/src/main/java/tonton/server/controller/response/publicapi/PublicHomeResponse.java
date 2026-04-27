package tonton.server.controller.response.publicapi;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PublicHomeResponse {
    private HomeStatsResponse stats;
    private List<PublicProductCardResponse> featuredProducts;
    private List<PublicProjectCardResponse> featuredProjects;
    private List<PublicPostCardResponse> latestPosts;
    private List<PublicServiceFeatureResponse> services;
}
