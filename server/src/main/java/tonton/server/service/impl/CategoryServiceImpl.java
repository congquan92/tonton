package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Category;
import tonton.server.repository.CategoryRepository;
import tonton.server.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends AbstractCrudService<Category> implements CategoryService {

    private final CategoryRepository repository;

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Category";
    }

    @Override
    protected void setEntityId(Category entity, Long id) {
        entity.setId(id);
    }
}