package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Post;
import tonton.server.repository.PostRepository;
import tonton.server.service.PostService;

@Service
@RequiredArgsConstructor
public class PostServiceImpl extends AbstractCrudService<Post> implements PostService {

    private final PostRepository repository;

    @Override
    protected JpaRepository<Post, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Post";
    }

    @Override
    protected void setEntityId(Post entity, Long id) {
        entity.setId(id);
    }
}