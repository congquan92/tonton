package tonton.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tonton.server.controller.response.common.PageResponse;
import tonton.server.controller.response.publicapi.PublicHomeResponse;
import tonton.server.controller.response.publicapi.PublicPostCardResponse;
import tonton.server.controller.response.publicapi.PublicPostDetailResponse;
import tonton.server.controller.response.publicapi.PublicProductCardResponse;
import tonton.server.controller.response.publicapi.PublicProductDetailResponse;
import tonton.server.controller.response.publicapi.PublicProjectCardResponse;
import tonton.server.controller.response.publicapi.PublicProjectDetailResponse;
import tonton.server.controller.response.publicapi.PublicServiceFeatureResponse;
import tonton.server.service.publicapi.PublicContentService;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicContentController {

    private final PublicContentService publicContentService;

    @GetMapping("/home")
    public ResponseEntity<PublicHomeResponse> getHomeContent() {
        return ResponseEntity.ok(publicContentService.getHomeContent());
    }

    @GetMapping("/products")
    public ResponseEntity<PageResponse<PublicProductCardResponse>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "newest") String sort
    ) {
        return ResponseEntity.ok(publicContentService.getPublicProducts(page, size, categoryId, keyword, sort));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<PublicProductDetailResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(publicContentService.getPublicProductById(id));
    }

    @GetMapping("/posts")
    public ResponseEntity<PageResponse<PublicPostCardResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(publicContentService.getPublicPosts(page, size, categoryId, keyword));
    }

    @GetMapping("/posts/{slug}")
    public ResponseEntity<PublicPostDetailResponse> getPostBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(publicContentService.getPublicPostBySlug(slug));
    }

    @GetMapping("/projects")
    public ResponseEntity<PageResponse<PublicProjectCardResponse>> getProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(publicContentService.getPublicProjects(page, size, category, keyword));
    }

    @GetMapping("/projects/{slug}")
    public ResponseEntity<PublicProjectDetailResponse> getProjectBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(publicContentService.getPublicProjectBySlug(slug));
    }

    @GetMapping("/services")
    public ResponseEntity<List<PublicServiceFeatureResponse>> getServices() {
        return ResponseEntity.ok(publicContentService.getPublicServices());
    }

    @GetMapping("/services/{slug}")
    public ResponseEntity<PublicServiceFeatureResponse> getServiceBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(publicContentService.getPublicServiceBySlug(slug));
    }
}
