package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.PriceTier;
import tonton.server.repository.PriceTierRepository;
import tonton.server.service.PriceTierService;

@Service
@RequiredArgsConstructor
public class PriceTierServiceImpl extends AbstractCrudService<PriceTier> implements PriceTierService {

    private final PriceTierRepository repository;

    @Override
    protected JpaRepository<PriceTier, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "PriceTier";
    }

    @Override
    protected void setEntityId(PriceTier entity, Long id) {
        entity.setId(id);
    }
}