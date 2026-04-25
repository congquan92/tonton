package tonton.server.service.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.exception.NotFoundException;
import tonton.server.service.CrudService;

import java.util.List;

@Transactional
public abstract class AbstractCrudService<T> implements CrudService<T> {

    protected abstract JpaRepository<T, Long> getRepository();

    protected abstract String getEntityName();

    protected abstract void setEntityId(T entity, Long id);

    @Override
    @Transactional(readOnly = true)
    public List<T> getAll() {
        return getRepository().findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public T getById(Long id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy " + getEntityName() + " với id: " + id));
    }

    @Override
    public T create(T entity) {
        return getRepository().save(entity);
    }

    @Override
    public T update(Long id, T entity) {
        getById(id);
        setEntityId(entity, id);
        return getRepository().save(entity);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        getRepository().deleteById(id);
    }
}
