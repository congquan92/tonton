package tonton.server.service.publicapi;

import tonton.server.controller.response.common.PageResponse;
import tonton.server.controller.response.publicapi.PublicHomeResponse;
import tonton.server.controller.response.publicapi.PublicPostCardResponse;
import tonton.server.controller.response.publicapi.PublicPostDetailResponse;
import tonton.server.controller.response.publicapi.PublicProductCardResponse;
import tonton.server.controller.response.publicapi.PublicProductDetailResponse;
import tonton.server.controller.response.publicapi.PublicProjectCardResponse;
import tonton.server.controller.response.publicapi.PublicProjectDetailResponse;
import tonton.server.controller.response.publicapi.PublicServiceFeatureResponse;

import java.util.List;

public interface PublicContentService {
    PageResponse<PublicProductCardResponse> getPublicProducts(
            int page,
            int size,
            Long categoryId,
            String keyword,
            String sort
    );

    PublicProductDetailResponse getPublicProductById(Long id);

    PageResponse<PublicPostCardResponse> getPublicPosts(
            int page,
            int size,
            Long categoryId,
            String keyword
    );

    PublicPostDetailResponse getPublicPostBySlug(String slug);

    PageResponse<PublicProjectCardResponse> getPublicProjects(
            int page,
            int size,
            String category,
            String keyword
    );

    PublicProjectDetailResponse getPublicProjectBySlug(String slug);

    List<PublicServiceFeatureResponse> getPublicServices();

    PublicServiceFeatureResponse getPublicServiceBySlug(String slug);

    PublicHomeResponse getHomeContent();
}
