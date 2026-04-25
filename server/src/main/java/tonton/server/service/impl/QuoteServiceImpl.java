package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.config.security.CurrentUserContext;
import tonton.server.exception.ForbiddenException;
import tonton.server.exception.NotFoundException;
import tonton.server.model.Quote;
import tonton.server.repository.QuoteRepository;
import tonton.server.service.QuoteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl extends AbstractCrudService<Quote> implements QuoteService {

    private final QuoteRepository repository;
    private final CurrentUserContext currentUserContext;

    @Override
    protected JpaRepository<Quote, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Quote";
    }

    @Override
    protected void setEntityId(Quote entity, Long id) {
        entity.setId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Quote> getAll() {
        if (currentUserContext.isPrivileged()) {
            return super.getAll();
        }
        return repository.findAllByUserId(currentUserContext.requireUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public Quote getById(Long id) {
        if (currentUserContext.isPrivileged()) {
            return super.getById(id);
        }
        Long userId = currentUserContext.requireUserId();
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy báo giá với id: " + id));
    }

    @Override
    public Quote create(Quote entity) {
        enforceOwnershipOnQuote(entity);
        return super.create(entity);
    }

    @Override
    public Quote update(Long id, Quote entity) {
        getById(id);
        enforceOwnershipOnQuote(entity);
        return super.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    private void enforceOwnershipOnQuote(Quote entity) {
        if (currentUserContext.isPrivileged()) {
            return;
        }
        Long currentUserId = currentUserContext.requireUserId();
        Long targetUserId = entity.getUser() != null ? entity.getUser().getId() : null;
        if (targetUserId == null || !targetUserId.equals(currentUserId)) {
            throw new ForbiddenException("Bạn chỉ có thể thao tác trên báo giá của chính mình");
        }
    }
}
