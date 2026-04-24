package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.QuoteItem;

@Repository
public interface QuoteItemRepository extends JpaRepository<QuoteItem, Long> {
}
