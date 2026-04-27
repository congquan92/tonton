package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import tonton.server.model.Project;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    Optional<Project> findBySlugAndIsActiveTrue(String slug);

    List<Project> findTop6ByIsActiveTrueAndFeaturedTrueOrderByCreatedAtDesc();

    List<Project> findTop6ByIsActiveTrueOrderByCreatedAtDesc();

    long countByIsActiveTrue();

    long countByIsActiveTrueAndFeaturedTrue();
}
