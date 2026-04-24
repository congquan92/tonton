package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.UserAddress;
import tonton.server.repository.UserAddressRepository;
import tonton.server.service.UserAddressService;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl extends AbstractCrudService<UserAddress> implements UserAddressService {

    private final UserAddressRepository repository;

    @Override
    protected JpaRepository<UserAddress, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "UserAddress";
    }

    @Override
    protected void setEntityId(UserAddress entity, Long id) {
        entity.setId(id);
    }
}