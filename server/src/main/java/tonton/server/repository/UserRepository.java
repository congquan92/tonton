package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
