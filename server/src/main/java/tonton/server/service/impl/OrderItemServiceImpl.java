package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.OrderItem;
import tonton.server.repository.OrderItemRepository;
import tonton.server.service.OrderItemService;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl extends AbstractCrudService<OrderItem> implements OrderItemService {

    private final OrderItemRepository repository;

    @Override
    protected JpaRepository<OrderItem, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "OrderItem";
    }

    @Override
    protected void setEntityId(OrderItem entity, Long id) {
        entity.setId(id);
    }
}