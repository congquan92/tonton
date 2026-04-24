package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Product;
import tonton.server.repository.ProductRepository;
import tonton.server.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl extends AbstractCrudService<Product> implements ProductService {

    private final ProductRepository repository;

    @Override
    protected JpaRepository<Product, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Product";
    }

    @Override
    protected void setEntityId(Product entity, Long id) {
        entity.setId(id);
    }
}