package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.ServiceFeature;
import tonton.server.repository.ServiceFeatureRepository;
import tonton.server.service.ServiceFeatureService;

@Service
@RequiredArgsConstructor
public class ServiceFeatureServiceImpl extends AbstractCrudService<ServiceFeature> implements ServiceFeatureService {

    private final ServiceFeatureRepository repository;

    @Override
    protected JpaRepository<ServiceFeature, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "ServiceFeature";
    }

    @Override
    protected void setEntityId(ServiceFeature entity, Long id) {
        entity.setId(id);
    }
}
