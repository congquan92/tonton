package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.UomConversion;
import tonton.server.repository.UomConversionRepository;
import tonton.server.service.UomConversionService;

@Service
@RequiredArgsConstructor
public class UomConversionServiceImpl extends AbstractCrudService<UomConversion> implements UomConversionService {

    private final UomConversionRepository repository;

    @Override
    protected JpaRepository<UomConversion, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "UomConversion";
    }

    @Override
    protected void setEntityId(UomConversion entity, Long id) {
        entity.setId(id);
    }
}