package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.Quote;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findAllByUserId(Long userId);

    Optional<Quote> findByIdAndUserId(Long id, Long userId);
}
