package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.config.security.CurrentUserContext;
import tonton.server.exception.ForbiddenException;
import tonton.server.exception.NotFoundException;
import tonton.server.model.UserAddress;
import tonton.server.repository.UserAddressRepository;
import tonton.server.service.UserAddressService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl extends AbstractCrudService<UserAddress> implements UserAddressService {

    private final UserAddressRepository repository;
    private final CurrentUserContext currentUserContext;

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

    @Override
    @Transactional(readOnly = true)
    public List<UserAddress> getAll() {
        if (currentUserContext.isPrivileged()) {
            return super.getAll();
        }
        return repository.findAllByUserId(currentUserContext.requireUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public UserAddress getById(Long id) {
        if (currentUserContext.isPrivileged()) {
            return super.getById(id);
        }
        Long userId = currentUserContext.requireUserId();
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy địa chỉ với id: " + id));
    }

    @Override
    public UserAddress create(UserAddress entity) {
        enforceOwnershipOnAddress(entity);
        return super.create(entity);
    }

    @Override
    public UserAddress update(Long id, UserAddress entity) {
        getById(id);
        enforceOwnershipOnAddress(entity);
        return super.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    private void enforceOwnershipOnAddress(UserAddress entity) {
        if (currentUserContext.isPrivileged()) {
            return;
        }
        Long currentUserId = currentUserContext.requireUserId();
        Long targetUserId = entity.getUser() != null ? entity.getUser().getId() : null;
        if (targetUserId == null || !targetUserId.equals(currentUserId)) {
            throw new ForbiddenException("Bạn chỉ có thể thao tác trên địa chỉ của chính mình");
        }
    }
}
