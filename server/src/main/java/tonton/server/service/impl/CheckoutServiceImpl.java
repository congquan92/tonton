package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.common.enums.CheckoutShippingMethod;
import tonton.server.common.enums.OrderStatus;
import tonton.server.common.enums.RoleName;
import tonton.server.config.security.CurrentUserContext;
import tonton.server.controller.request.checkout.CheckoutRequest;
import tonton.server.controller.response.checkout.CheckoutResponse;
import tonton.server.exception.BadRequestException;
import tonton.server.exception.NotFoundException;
import tonton.server.model.Cart;
import tonton.server.model.CartItem;
import tonton.server.model.Order;
import tonton.server.model.OrderItem;
import tonton.server.model.User;
import tonton.server.model.UserAddress;
import tonton.server.repository.CartItemRepository;
import tonton.server.repository.CartRepository;
import tonton.server.repository.OrderItemRepository;
import tonton.server.repository.OrderRepository;
import tonton.server.repository.UserAddressRepository;
import tonton.server.repository.UserRepository;
import tonton.server.service.CheckoutService;
import tonton.server.service.PricingService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private static final BigDecimal SHIPPING_FEE_STANDARD = new BigDecimal("0");
    private static final BigDecimal SHIPPING_FEE_EXPRESS = new BigDecimal("100000");

    private final CurrentUserContext currentUserContext;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final UserAddressRepository userAddressRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PricingService pricingService;

    @Override
    @Transactional
    public CheckoutResponse placeOrder(CheckoutRequest request) {
        Long userId = currentUserContext.requireUserId();
        RoleName roleName = currentUserContext.requirePrincipal().getRoleName();

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Giỏ hàng đang trống"));
        List<CartItem> cartItems = cartItemRepository.findAllByCartId(cart.getId());
        if (cartItems.isEmpty()) {
            throw new BadRequestException("Giỏ hàng đang trống");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng với id: " + userId));

        BigDecimal subtotal = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PROCESSING);
        order.setPaymentMethod(request.getPaymentMethod());

        for (CartItem cartItem : cartItems) {
            BigDecimal qty = cartItem.getQuantity() == null ? BigDecimal.ZERO : cartItem.getQuantity();
            BigDecimal unitPrice = pricingService.resolveUnitPrice(cartItem.getProduct(), qty, roleName);
            BigDecimal lineTotal = unitPrice.multiply(qty).setScale(2, RoundingMode.HALF_UP);
            subtotal = subtotal.add(lineTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setUom(cartItem.getUom());
            orderItem.setQuantity(qty.setScale(2, RoundingMode.HALF_UP));
            orderItem.setPrice(unitPrice.setScale(2, RoundingMode.HALF_UP));
            orderItems.add(orderItem);
        }

        BigDecimal shippingFee = resolveShippingFee(request.getShippingMethod());
        BigDecimal vatAmount = subtotal.multiply(CartServiceImpl.VAT_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = subtotal.add(vatAmount).add(shippingFee).setScale(2, RoundingMode.HALF_UP);

        order.setShippingFee(shippingFee);
        order.setTotalAmount(totalAmount);
        order.setShippingAddressSnapshot(resolveShippingSnapshot(userId, request));

        if (request.getShippingAddressId() != null) {
            UserAddress userAddress = userAddressRepository.findByIdAndUserId(request.getShippingAddressId(), userId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy địa chỉ giao hàng với id: " + request.getShippingAddressId()));
            order.setShippingAddress(userAddress);
        }

        Order savedOrder = orderRepository.save(order);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(savedOrder);
        }
        orderItemRepository.saveAll(orderItems);

        cartItemRepository.deleteByCartId(cart.getId());

        return CheckoutResponse.builder()
                .orderId(savedOrder.getId())
                .status(savedOrder.getStatus())
                .paymentMethod(savedOrder.getPaymentMethod())
                .shippingMethod(request.getShippingMethod())
                .subtotal(subtotal.setScale(2, RoundingMode.HALF_UP))
                .vatAmount(vatAmount)
                .shippingFee(shippingFee)
                .totalAmount(totalAmount)
                .itemCount(orderItems.size())
                .build();
    }

    private String resolveShippingSnapshot(Long userId, CheckoutRequest request) {
        if (request.getShippingAddressId() != null) {
            UserAddress userAddress = userAddressRepository.findByIdAndUserId(request.getShippingAddressId(), userId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy địa chỉ giao hàng với id: " + request.getShippingAddressId()));
            return "%s - %s - %s".formatted(
                    userAddress.getReceiverName(),
                    userAddress.getReceiverPhone(),
                    userAddress.getAddress()
            );
        }
        if (request.getShippingAddressSnapshot() == null || request.getShippingAddressSnapshot().isBlank()) {
            throw new BadRequestException("Thiếu thông tin địa chỉ giao hàng");
        }
        return request.getShippingAddressSnapshot();
    }

    private BigDecimal resolveShippingFee(CheckoutShippingMethod shippingMethod) {
        if (shippingMethod == null || shippingMethod == CheckoutShippingMethod.STANDARD) {
            return SHIPPING_FEE_STANDARD.setScale(2, RoundingMode.HALF_UP);
        }
        return SHIPPING_FEE_EXPRESS.setScale(2, RoundingMode.HALF_UP);
    }
}
