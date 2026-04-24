package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.Uom;

@Repository
public interface UomRepository extends JpaRepository<Uom, Long> {
}
