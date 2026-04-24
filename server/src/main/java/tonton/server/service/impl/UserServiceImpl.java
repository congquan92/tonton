package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.User;
import tonton.server.repository.UserRepository;
import tonton.server.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {

    private final UserRepository repository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "User";
    }

    @Override
    protected void setEntityId(User entity, Long id) {
        entity.setId(id);
    }
}