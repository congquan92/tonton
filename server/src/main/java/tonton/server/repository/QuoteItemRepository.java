package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.QuoteItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteItemRepository extends JpaRepository<QuoteItem, Long> {
    List<QuoteItem> findAllByQuoteUserId(Long userId);

    Optional<QuoteItem> findByIdAndQuoteUserId(Long id, Long userId);
}
