package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.PostCategory;
import tonton.server.repository.PostCategoryRepository;
import tonton.server.service.PostCategoryService;

@Service
@RequiredArgsConstructor
public class PostCategoryServiceImpl extends AbstractCrudService<PostCategory> implements PostCategoryService {

    private final PostCategoryRepository repository;

    @Override
    protected JpaRepository<PostCategory, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "PostCategory";
    }

    @Override
    protected void setEntityId(PostCategory entity, Long id) {
        entity.setId(id);
    }
}