package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.ProductImage;
import tonton.server.repository.ProductImageRepository;
import tonton.server.service.ProductImageService;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl extends AbstractCrudService<ProductImage> implements ProductImageService {

    private final ProductImageRepository repository;

    @Override
    protected JpaRepository<ProductImage, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "ProductImage";
    }

    @Override
    protected void setEntityId(ProductImage entity, Long id) {
        entity.setId(id);
    }
}