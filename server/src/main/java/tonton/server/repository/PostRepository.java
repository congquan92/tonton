package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
