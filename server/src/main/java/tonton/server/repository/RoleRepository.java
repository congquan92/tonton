package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
