package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Uom;
import tonton.server.repository.UomRepository;
import tonton.server.service.UomService;

@Service
@RequiredArgsConstructor
public class UomServiceImpl extends AbstractCrudService<Uom> implements UomService {

    private final UomRepository repository;

    @Override
    protected JpaRepository<Uom, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Uom";
    }

    @Override
    protected void setEntityId(Uom entity, Long id) {
        entity.setId(id);
    }
}