package tonton.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tonton.server.model.UomConversion;

@Repository
public interface UomConversionRepository extends JpaRepository<UomConversion, Long> {
}
