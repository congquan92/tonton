package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
