package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.PriceTier;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {
}
