package tonton.server.service.impl.publicapi;

import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.common.enums.PostStatus;
import tonton.server.common.enums.RoleName;
import tonton.server.config.security.AppUserPrincipal;
import tonton.server.controller.response.common.PageResponse;
import tonton.server.controller.response.publicapi.HomeStatsResponse;
import tonton.server.controller.response.publicapi.PublicHomeResponse;
import tonton.server.controller.response.publicapi.PublicPostCardResponse;
import tonton.server.controller.response.publicapi.PublicPostDetailResponse;
import tonton.server.controller.response.publicapi.PublicPriceTierResponse;
import tonton.server.controller.response.publicapi.PublicProductCardResponse;
import tonton.server.controller.response.publicapi.PublicProductDetailResponse;
import tonton.server.controller.response.publicapi.PublicProjectCardResponse;
import tonton.server.controller.response.publicapi.PublicProjectDetailResponse;
import tonton.server.controller.response.publicapi.PublicServiceFeatureResponse;
import tonton.server.exception.NotFoundException;
import tonton.server.model.Post;
import tonton.server.model.Product;
import tonton.server.model.ProductImage;
import tonton.server.model.Project;
import tonton.server.model.ServiceFeature;
import tonton.server.repository.PostRepository;
import tonton.server.repository.PriceTierRepository;
import tonton.server.repository.ProductImageRepository;
import tonton.server.repository.ProductRepository;
import tonton.server.repository.ProjectRepository;
import tonton.server.repository.ServiceFeatureRepository;
import tonton.server.service.PricingService;
import tonton.server.service.publicapi.PublicContentService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PublicContentServiceImpl implements PublicContentService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final PriceTierRepository priceTierRepository;
    private final PricingService pricingService;
    private final PostRepository postRepository;
    private final ProjectRepository projectRepository;
    private final ServiceFeatureRepository serviceFeatureRepository;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PublicProductCardResponse> getPublicProducts(int page, int size, Long categoryId, String keyword, String sort) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 100);
        RoleName roleName = resolveCurrentRoleOrDefault();

        Specification<Product> specification = publicProductSpec(categoryId, keyword);

        if ("price_asc".equalsIgnoreCase(sort) || "price_desc".equalsIgnoreCase(sort)) {
            List<Product> allProducts = productRepository.findAll(specification);
            List<PublicProductCardResponse> mapped = mapPublicProductCards(allProducts, roleName);
            Comparator<PublicProductCardResponse> comparator = Comparator.comparing(
                    PublicProductCardResponse::getDisplayPrice,
                    Comparator.nullsLast(BigDecimal::compareTo)
            );
            if ("price_desc".equalsIgnoreCase(sort)) {
                comparator = comparator.reversed();
            }
            mapped.sort(comparator);
            return paginateList(mapped, normalizedPage, normalizedSize);
        }

        Pageable pageable = PageRequest.of(normalizedPage, normalizedSize, resolveProductSort(sort));
        Page<Product> productPage = productRepository.findAll(specification, pageable);
        List<PublicProductCardResponse> mapped = mapPublicProductCards(productPage.getContent(), roleName);
        Page<PublicProductCardResponse> mappedPage = new PageImpl<>(mapped, pageable, productPage.getTotalElements());
        return PageResponse.fromPage(mappedPage);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicProductDetailResponse getPublicProductById(Long id) {
        Product product = productRepository.findById(id)
                .filter(p -> !Boolean.FALSE.equals(p.getIsActive()))
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với id: " + id));

        List<ProductImage> images = productImageRepository.findByProductIdInOrderByProductIdAscSortOrderAsc(List.of(product.getId()));
        List<String> imageUrls = images.stream().map(ProductImage::getImageUrl).toList();
        String primaryImage = imageUrls.isEmpty() ? null : imageUrls.getFirst();

        List<PublicPriceTierResponse> priceTiers = priceTierRepository.findByProductIdOrderByMinQtyAsc(product.getId())
                .stream()
                .map(tier -> PublicPriceTierResponse.builder()
                        .minQty(tier.getMinQty())
                        .price(tier.getPrice())
                        .role(tier.getRole() != null && tier.getRole().getName() != null ? tier.getRole().getName().name() : null)
                        .build())
                .toList();

        BigDecimal displayPrice = pricingService.resolveUnitPrice(product, BigDecimal.ONE, resolveCurrentRoleOrDefault());

        return PublicProductDetailResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .baseUomId(product.getBaseUom() != null ? product.getBaseUom().getId() : null)
                .baseUomName(product.getBaseUom() != null ? product.getBaseUom().getName() : null)
                .baseUomSymbol(product.getBaseUom() != null ? product.getBaseUom().getSymbol() : null)
                .quantity(product.getQuantity())
                .attributes(product.getAttributes())
                .primaryImageUrl(primaryImage)
                .imageUrls(imageUrls)
                .priceTiers(priceTiers)
                .displayPrice(displayPrice)
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PublicPostCardResponse> getPublicPosts(int page, int size, Long categoryId, String keyword) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(normalizedPage, normalizedSize, Sort.by(Sort.Direction.DESC, "publishedAt"));

        Specification<Post> specification = publicPostSpec(categoryId, keyword);
        Page<Post> postPage = postRepository.findAll(specification, pageable);
        List<PublicPostCardResponse> content = postPage.getContent().stream().map(this::mapPostCard).toList();
        return PageResponse.fromPage(new PageImpl<>(content, pageable, postPage.getTotalElements()));
    }

    @Override
    @Transactional
    public PublicPostDetailResponse getPublicPostBySlug(String slug) {
        Post post = postRepository.findBySlugAndStatus(slug, PostStatus.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bài viết với slug: " + slug));

        int currentViewCount = post.getViewCount() == null ? 0 : post.getViewCount();
        post.setViewCount(currentViewCount + 1);
        Post updated = postRepository.save(post);

        Long categoryId = updated.getCategory() != null ? updated.getCategory().getId() : null;
        List<PublicPostCardResponse> relatedPosts;
        if (categoryId != null) {
            relatedPosts = postRepository.findTop4ByCategoryIdAndStatusAndIdNotOrderByPublishedAtDesc(
                            categoryId,
                            PostStatus.PUBLISHED,
                            updated.getId()
                    ).stream()
                    .map(this::mapPostCard)
                    .toList();
        } else {
            relatedPosts = List.of();
        }

        return PublicPostDetailResponse.builder()
                .id(updated.getId())
                .title(updated.getTitle())
                .slug(updated.getSlug())
                .thumbnailUrl(updated.getThumbnailUrl())
                .summary(updated.getSummary())
                .content(updated.getContent())
                .categoryId(updated.getCategory() != null ? updated.getCategory().getId() : null)
                .categoryName(updated.getCategory() != null ? updated.getCategory().getName() : null)
                .authorName(updated.getAuthor() != null ? updated.getAuthor().getFullName() : null)
                .viewCount(updated.getViewCount())
                .publishedAt(updated.getPublishedAt())
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt())
                .relatedPosts(relatedPosts)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<PublicProjectCardResponse> getPublicProjects(int page, int size, String category, String keyword) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 100);
        Pageable pageable = PageRequest.of(normalizedPage, normalizedSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Specification<Project> specification = publicProjectSpec(category, keyword);
        Page<Project> projectPage = projectRepository.findAll(specification, pageable);
        List<PublicProjectCardResponse> content = projectPage.getContent().stream().map(this::mapProjectCard).toList();
        return PageResponse.fromPage(new PageImpl<>(content, pageable, projectPage.getTotalElements()));
    }

    @Override
    @Transactional(readOnly = true)
    public PublicProjectDetailResponse getPublicProjectBySlug(String slug) {
        Project project = projectRepository.findBySlugAndIsActiveTrue(slug)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy dự án với slug: " + slug));

        return PublicProjectDetailResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .thumbnailUrl(project.getThumbnailUrl())
                .summary(project.getSummary())
                .content(project.getContent())
                .category(project.getCategory())
                .location(project.getLocation())
                .completedAt(project.getCompletedAt())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicServiceFeatureResponse> getPublicServices() {
        return serviceFeatureRepository.findByIsActiveTrueOrderBySortOrderAscIdAsc().stream()
                .map(this::mapService)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PublicServiceFeatureResponse getPublicServiceBySlug(String slug) {
        ServiceFeature serviceFeature = serviceFeatureRepository.findBySlugAndIsActiveTrue(slug)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy dịch vụ với slug: " + slug));
        return mapService(serviceFeature);
    }

    @Override
    @Transactional(readOnly = true)
    public PublicHomeResponse getHomeContent() {
        RoleName roleName = resolveCurrentRoleOrDefault();

        List<PublicProductCardResponse> featuredProducts = mapPublicProductCards(
                productRepository.findTop8ByIsActiveTrueOrderByCreatedAtDesc(),
                roleName
        );

        List<Project> featuredProjectsRaw = projectRepository.findTop6ByIsActiveTrueAndFeaturedTrueOrderByCreatedAtDesc();
        if (featuredProjectsRaw.isEmpty()) {
            featuredProjectsRaw = projectRepository.findTop6ByIsActiveTrueOrderByCreatedAtDesc();
        }
        List<PublicProjectCardResponse> featuredProjects = featuredProjectsRaw.stream().map(this::mapProjectCard).toList();

        List<PublicPostCardResponse> latestPosts = postRepository.findTop4ByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED)
                .stream()
                .map(this::mapPostCard)
                .toList();

        List<PublicServiceFeatureResponse> services = getPublicServices();

        HomeStatsResponse stats = HomeStatsResponse.builder()
                .activeProducts(productRepository.countByIsActiveTrue())
                .featuredProjects(projectRepository.countByIsActiveTrueAndFeaturedTrue())
                .publishedPosts(postRepository.countByStatus(PostStatus.PUBLISHED))
                .activeServices(serviceFeatureRepository.countByIsActiveTrue())
                .build();

        return PublicHomeResponse.builder()
                .stats(stats)
                .featuredProducts(featuredProducts)
                .featuredProjects(featuredProjects)
                .latestPosts(latestPosts)
                .services(services)
                .build();
    }

    private PublicPostCardResponse mapPostCard(Post post) {
        return PublicPostCardResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .slug(post.getSlug())
                .thumbnailUrl(post.getThumbnailUrl())
                .summary(post.getSummary())
                .categoryId(post.getCategory() != null ? post.getCategory().getId() : null)
                .categoryName(post.getCategory() != null ? post.getCategory().getName() : null)
                .viewCount(post.getViewCount())
                .publishedAt(post.getPublishedAt())
                .build();
    }

    private PublicProjectCardResponse mapProjectCard(Project project) {
        return PublicProjectCardResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .slug(project.getSlug())
                .thumbnailUrl(project.getThumbnailUrl())
                .summary(project.getSummary())
                .category(project.getCategory())
                .location(project.getLocation())
                .completedAt(project.getCompletedAt())
                .build();
    }

    private PublicServiceFeatureResponse mapService(ServiceFeature serviceFeature) {
        return PublicServiceFeatureResponse.builder()
                .id(serviceFeature.getId())
                .name(serviceFeature.getName())
                .slug(serviceFeature.getSlug())
                .iconUrl(serviceFeature.getIconUrl())
                .shortDescription(serviceFeature.getShortDescription())
                .content(serviceFeature.getContent())
                .sortOrder(serviceFeature.getSortOrder())
                .build();
    }

    private List<PublicProductCardResponse> mapPublicProductCards(Collection<Product> products, RoleName roleName) {
        if (products == null || products.isEmpty()) {
            return List.of();
        }
        List<Product> list = products.stream().filter(Objects::nonNull).toList();
        List<Long> productIds = list.stream().map(Product::getId).filter(Objects::nonNull).distinct().toList();

        Map<Long, String> primaryImages = resolvePrimaryImages(productIds);
        Map<Long, BigDecimal> prices = pricingService.resolveUnitPriceForProducts(productIds, BigDecimal.ONE, roleName);

        return list.stream().map(product -> PublicProductCardResponse.builder()
                        .id(product.getId())
                        .sku(product.getSku())
                        .name(product.getName())
                        .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                        .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                        .baseUomSymbol(product.getBaseUom() != null ? product.getBaseUom().getSymbol() : null)
                        .primaryImageUrl(primaryImages.get(product.getId()))
                        .displayPrice(normalizeMoney(prices.getOrDefault(product.getId(), BigDecimal.ZERO)))
                        .build())
                .toList();
    }

    private Map<Long, String> resolvePrimaryImages(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ProductImage> images = productImageRepository.findByProductIdInOrderByProductIdAscSortOrderAsc(productIds);
        Map<Long, String> result = new HashMap<>();
        for (ProductImage image : images) {
            if (image.getProduct() == null || image.getProduct().getId() == null) {
                continue;
            }
            result.putIfAbsent(image.getProduct().getId(), image.getImageUrl());
        }
        return result;
    }

    private Specification<Product> publicProductSpec(Long categoryId, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isTrue(root.get("isActive")));
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), pattern),
                        cb.like(cb.lower(root.get("sku")), pattern)
                ));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private Specification<Post> publicPostSpec(Long categoryId, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("status"), PostStatus.PUBLISHED));
            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }
            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), pattern),
                        cb.like(cb.lower(root.get("summary")), pattern),
                        cb.like(cb.lower(root.get("content")), pattern)
                ));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private Specification<Project> publicProjectSpec(String category, String keyword) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.isTrue(root.get("isActive")));
            if (category != null && !category.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("category")), category.trim().toLowerCase()));
            }
            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.trim().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("title")), pattern),
                        cb.like(cb.lower(root.get("summary")), pattern),
                        cb.like(cb.lower(root.get("content")), pattern),
                        cb.like(cb.lower(root.get("location")), pattern)
                ));
            }
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }

    private Sort resolveProductSort(String sort) {
        if (sort == null || sort.isBlank() || "newest".equalsIgnoreCase(sort)) {
            return Sort.by(Sort.Direction.DESC, "createdAt");
        }
        return switch (sort.toLowerCase()) {
            case "oldest" -> Sort.by(Sort.Direction.ASC, "createdAt");
            case "name_asc" -> Sort.by(Sort.Direction.ASC, "name");
            case "name_desc" -> Sort.by(Sort.Direction.DESC, "name");
            default -> Sort.by(Sort.Direction.DESC, "createdAt");
        };
    }

    private <T> PageResponse<T> paginateList(List<T> items, int page, int size) {
        int total = items.size();
        int start = Math.min(page * size, total);
        int end = Math.min(start + size, total);
        List<T> content = start >= end ? List.of() : items.subList(start, end);
        Page<T> data = new PageImpl<>(content, PageRequest.of(page, size), total);
        return PageResponse.fromPage(data);
    }

    private RoleName resolveCurrentRoleOrDefault() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return RoleName.B2C;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof AppUserPrincipal appUserPrincipal) {
            return appUserPrincipal.getRoleName();
        }
        return RoleName.B2C;
    }

    private BigDecimal normalizeMoney(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
