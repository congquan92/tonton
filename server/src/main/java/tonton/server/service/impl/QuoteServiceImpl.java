package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Quote;
import tonton.server.repository.QuoteRepository;
import tonton.server.service.QuoteService;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl extends AbstractCrudService<Quote> implements QuoteService {

    private final QuoteRepository repository;

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
}