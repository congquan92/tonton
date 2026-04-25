package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.config.security.CurrentUserContext;
import tonton.server.exception.ForbiddenException;
import tonton.server.exception.NotFoundException;
import tonton.server.model.QuoteItem;
import tonton.server.repository.QuoteRepository;
import tonton.server.repository.QuoteItemRepository;
import tonton.server.service.QuoteItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteItemServiceImpl extends AbstractCrudService<QuoteItem> implements QuoteItemService {

    private final QuoteItemRepository repository;
    private final QuoteRepository quoteRepository;
    private final CurrentUserContext currentUserContext;

    @Override
    protected JpaRepository<QuoteItem, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "QuoteItem";
    }

    @Override
    protected void setEntityId(QuoteItem entity, Long id) {
        entity.setId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuoteItem> getAll() {
        if (currentUserContext.isPrivileged()) {
            return super.getAll();
        }
        return repository.findAllByQuoteUserId(currentUserContext.requireUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public QuoteItem getById(Long id) {
        if (currentUserContext.isPrivileged()) {
            return super.getById(id);
        }
        Long userId = currentUserContext.requireUserId();
        return repository.findByIdAndQuoteUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy chi tiết báo giá với id: " + id));
    }

    @Override
    public QuoteItem create(QuoteItem entity) {
        enforceOwnershipOnQuoteItem(entity);
        return super.create(entity);
    }

    @Override
    public QuoteItem update(Long id, QuoteItem entity) {
        getById(id);
        enforceOwnershipOnQuoteItem(entity);
        return super.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    private void enforceOwnershipOnQuoteItem(QuoteItem entity) {
        if (currentUserContext.isPrivileged()) {
            return;
        }
        Long quoteId = entity.getQuote() != null ? entity.getQuote().getId() : null;
        if (quoteId == null) {
            throw new ForbiddenException("Báo giá là bắt buộc");
        }
        Long currentUserId = currentUserContext.requireUserId();
        if (quoteRepository.findByIdAndUserId(quoteId, currentUserId).isEmpty()) {
            throw new ForbiddenException("Bạn chỉ có thể thao tác trên chi tiết báo giá thuộc báo giá của chính mình");
        }
    }
}
