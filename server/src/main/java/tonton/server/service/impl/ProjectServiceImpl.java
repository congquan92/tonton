package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Project;
import tonton.server.repository.ProjectRepository;
import tonton.server.service.ProjectService;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl extends AbstractCrudService<Project> implements ProjectService {

    private final ProjectRepository repository;

    @Override
    protected JpaRepository<Project, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Project";
    }

    @Override
    protected void setEntityId(Project entity, Long id) {
        entity.setId(id);
    }
}
