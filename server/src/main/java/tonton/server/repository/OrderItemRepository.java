package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.OrderItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrderUserId(Long userId);

    Optional<OrderItem> findByIdAndOrderUserId(Long id, Long userId);
}
