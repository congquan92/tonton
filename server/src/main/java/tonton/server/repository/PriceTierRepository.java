package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.common.enums.RoleName;
import tonton.server.model.PriceTier;

import java.util.Collection;
import java.util.List;

@Repository
public interface PriceTierRepository extends JpaRepository<PriceTier, Long> {
    List<PriceTier> findByProductIdInAndRole_NameOrderByProductIdAscMinQtyDesc(Collection<Long> productIds, RoleName roleName);

    List<PriceTier> findByProductIdOrderByMinQtyAsc(Long productId);
}
