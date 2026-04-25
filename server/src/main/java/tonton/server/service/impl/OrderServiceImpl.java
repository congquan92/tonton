package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.config.security.CurrentUserContext;
import tonton.server.exception.ForbiddenException;
import tonton.server.exception.NotFoundException;
import tonton.server.model.Order;
import tonton.server.repository.OrderRepository;
import tonton.server.repository.QuoteRepository;
import tonton.server.repository.UserAddressRepository;
import tonton.server.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends AbstractCrudService<Order> implements OrderService {

    private final OrderRepository repository;
    private final QuoteRepository quoteRepository;
    private final UserAddressRepository userAddressRepository;
    private final CurrentUserContext currentUserContext;

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

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAll() {
        if (currentUserContext.isPrivileged()) {
            return super.getAll();
        }
        return repository.findAllByUserId(currentUserContext.requireUserId());
    }

    @Override
    @Transactional(readOnly = true)
    public Order getById(Long id) {
        if (currentUserContext.isPrivileged()) {
            return super.getById(id);
        }
        Long userId = currentUserContext.requireUserId();
        return repository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn hàng với id: " + id));
    }

    @Override
    public Order create(Order entity) {
        enforceOwnershipOnOrder(entity);
        return super.create(entity);
    }

    @Override
    public Order update(Long id, Order entity) {
        getById(id);
        enforceOwnershipOnOrder(entity);
        return super.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    private void enforceOwnershipOnOrder(Order entity) {
        if (currentUserContext.isPrivileged()) {
            return;
        }
        Long currentUserId = currentUserContext.requireUserId();
        Long targetUserId = entity.getUser() != null ? entity.getUser().getId() : null;
        if (targetUserId == null || !targetUserId.equals(currentUserId)) {
            throw new ForbiddenException("Bạn chỉ có thể thao tác trên đơn hàng của chính mình");
        }

        Long quoteId = entity.getQuote() != null ? entity.getQuote().getId() : null;
        if (quoteId != null && quoteRepository.findByIdAndUserId(quoteId, currentUserId).isEmpty()) {
            throw new ForbiddenException("Bạn chỉ có thể sử dụng báo giá của chính mình");
        }

        Long shippingAddressId = entity.getShippingAddress() != null ? entity.getShippingAddress().getId() : null;
        if (shippingAddressId != null && userAddressRepository.findByIdAndUserId(shippingAddressId, currentUserId).isEmpty()) {
            throw new ForbiddenException("Bạn chỉ có thể sử dụng địa chỉ giao hàng của chính mình");
        }
    }
}
