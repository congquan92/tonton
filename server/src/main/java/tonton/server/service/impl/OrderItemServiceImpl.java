package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.config.security.CurrentUserContext;
import tonton.server.exception.ForbiddenException;
import tonton.server.exception.NotFoundException;
import tonton.server.model.OrderItem;
import tonton.server.repository.OrderRepository;
import tonton.server.repository.OrderItemRepository;
import tonton.server.service.OrderItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl extends AbstractCrudService<OrderItem> implements OrderItemService {

    private final OrderItemRepository repository;
    private final OrderRepository orderRepository;
    private final CurrentUserContext currentUserContext;

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

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> getAll() {
        if (currentUserContext.isPrivileged()) {
            return super.getAll();
        }
        return repository.findAllByOrderUserId(currentUserContext.requireUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItem getById(Long id) {
        if (currentUserContext.isPrivileged()) {
            return super.getById(id);
        }
        Long userId = currentUserContext.requireUserId();
        return repository.findByIdAndOrderUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy chi tiết đơn hàng với id: " + id));
    }

    @Override
    public OrderItem create(OrderItem entity) {
        enforceOwnershipOnOrderItem(entity);
        return super.create(entity);
    }

    @Override
    public OrderItem update(Long id, OrderItem entity) {
        getById(id);
        enforceOwnershipOnOrderItem(entity);
        return super.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    private void enforceOwnershipOnOrderItem(OrderItem entity) {
        if (currentUserContext.isPrivileged()) {
            return;
        }
        Long orderId = entity.getOrder() != null ? entity.getOrder().getId() : null;
        if (orderId == null) {
            throw new ForbiddenException("Đơn hàng là bắt buộc");
        }
        Long currentUserId = currentUserContext.requireUserId();
        if (orderRepository.findByIdAndUserId(orderId, currentUserId).isEmpty()) {
            throw new ForbiddenException("Bạn chỉ có thể thao tác trên chi tiết đơn hàng thuộc đơn của chính mình");
        }
    }
}
