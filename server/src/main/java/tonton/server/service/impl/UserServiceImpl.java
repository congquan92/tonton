package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tonton.server.model.User;
import tonton.server.repository.UserRepository;
import tonton.server.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends AbstractCrudService<User> implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    public User create(User entity) {
        preparePassword(entity);
        normalizeTokenVersion(entity);
        return super.create(entity);
    }

    @Override
    public User update(Long id, User entity) {
        preparePassword(entity);
        normalizeTokenVersion(entity);
        return super.update(id, entity);
    }

    private void preparePassword(User entity) {
        String password = entity.getPassword();
        if (password == null || password.isBlank()) {
            return;
        }
        if (password.startsWith("$2a$") || password.startsWith("$2b$") || password.startsWith("$2y$")) {
            return;
        }
        entity.setPassword(passwordEncoder.encode(password));
    }

    private void normalizeTokenVersion(User entity) {
        if (entity.getTokenVersion() == null || entity.getTokenVersion() < 1) {
            entity.setTokenVersion(1);
        }
    }
}
