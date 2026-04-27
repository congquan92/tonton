package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tonton.server.common.enums.PostStatus;
import tonton.server.model.Post;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
    Optional<Post> findBySlugAndStatus(String slug, PostStatus status);

    List<Post> findTop4ByStatusOrderByPublishedAtDesc(PostStatus status);

    List<Post> findTop4ByCategoryIdAndStatusAndIdNotOrderByPublishedAtDesc(Long categoryId, PostStatus status, Long id);

    long countByStatus(PostStatus status);
}
