package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {
}
