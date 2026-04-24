package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Role;
import tonton.server.repository.RoleRepository;
import tonton.server.service.RoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends AbstractCrudService<Role> implements RoleService {

    private final RoleRepository repository;

    @Override
    protected JpaRepository<Role, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Role";
    }

    @Override
    protected void setEntityId(Role entity, Long id) {
        entity.setId(id);
    }
}