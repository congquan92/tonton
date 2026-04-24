package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import tonton.server.model.Order;
import tonton.server.repository.OrderRepository;
import tonton.server.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends AbstractCrudService<Order> implements OrderService {

    private final OrderRepository repository;

    @Override
    protected JpaRepository<Order, Long> getRepository() {
        return repository;
    }

    @Override
    protected String getEntityName() {
        return "Order";
    }

    @Override
    protected void setEntityId(Order entity, Long id) {
        entity.setId(id);
    }
}