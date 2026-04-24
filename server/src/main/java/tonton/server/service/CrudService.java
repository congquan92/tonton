package tonton.server.service;

import java.util.List;

public interface CrudService<T> {
    List<T> getAll();

    T getById(Long id);

    T create(T entity);

    T update(Long id, T entity);

    void delete(Long id);
}