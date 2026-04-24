package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.QuoteItem;
import tonton.server.repository.QuoteItemRepository;
import tonton.server.service.QuoteItemService;

@Service
@RequiredArgsConstructor
public class QuoteItemServiceImpl extends AbstractCrudService<QuoteItem> implements QuoteItemService {

    private final QuoteItemRepository repository;

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
}