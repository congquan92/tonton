package tonton.server.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tonton.server.common.enums.RoleName;
import tonton.server.config.security.CurrentUserContext;
import tonton.server.controller.request.cart.CartItemUpsertRequest;
import tonton.server.controller.response.cart.CartItemResponse;
import tonton.server.controller.response.cart.CartResponse;
import tonton.server.exception.BadRequestException;
import tonton.server.exception.NotFoundException;
import tonton.server.model.Cart;
import tonton.server.model.CartItem;
import tonton.server.model.Product;
import tonton.server.model.ProductImage;
import tonton.server.model.Uom;
import tonton.server.model.User;
import tonton.server.repository.CartItemRepository;
import tonton.server.repository.CartRepository;
import tonton.server.repository.ProductImageRepository;
import tonton.server.repository.ProductRepository;
import tonton.server.repository.UomRepository;
import tonton.server.repository.UserRepository;
import tonton.server.service.CartService;
import tonton.server.service.PricingService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    public static final BigDecimal VAT_RATE = new BigDecimal("0.10");

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UomRepository uomRepository;
    private final UserRepository userRepository;
    private final ProductImageRepository productImageRepository;
    private final PricingService pricingService;
    private final CurrentUserContext currentUserContext;

    @Override
    @Transactional(readOnly = true)
    public CartResponse getMyCart() {
        Long userId = currentUserContext.requireUserId();
        RoleName roleName = currentUserContext.requirePrincipal().getRoleName();

        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            return emptyCartResponse(userId);
        }
        return buildCartResponse(cart, roleName, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    @Transactional
    public CartResponse addItem(CartItemUpsertRequest request) {
        if (request.getQuantity() == null || request.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Số lượng sản phẩm phải lớn hơn 0");
        }

        Long userId = currentUserContext.requireUserId();
        Cart cart = findOrCreateCart(userId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với id: " + request.getProductId()));
        if (Boolean.FALSE.equals(product.getIsActive())) {
            throw new BadRequestException("Sản phẩm này hiện không khả dụng");
        }

        Uom uom = resolveUom(product, request.getUomId());
        CartItem item = cartItemRepository.findByCartIdAndProductIdAndUomId(cart.getId(), product.getId(), uom.getId())
                .orElseGet(CartItem::new);

        if (item.getId() == null) {
            item.setCart(cart);
            item.setProduct(product);
            item.setUom(uom);
            item.setQuantity(request.getQuantity().setScale(2, RoundingMode.HALF_UP));
        } else {
            item.setQuantity(item.getQuantity().add(request.getQuantity()).setScale(2, RoundingMode.HALF_UP));
        }
        cartItemRepository.save(item);

        RoleName roleName = currentUserContext.requirePrincipal().getRoleName();
        return buildCartResponse(cart, roleName, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    @Transactional
    public CartResponse updateItemQuantity(Long itemId, BigDecimal quantity) {
        Long userId = currentUserContext.requireUserId();
        CartItem item = cartItemRepository.findByIdAndCartUserId(itemId, userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm trong giỏ với id: " + itemId));

        if (quantity == null || quantity.compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Số lượng không hợp lệ");
        }

        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(quantity.setScale(2, RoundingMode.HALF_UP));
            cartItemRepository.save(item);
        }

        RoleName roleName = currentUserContext.requirePrincipal().getRoleName();
        return buildCartResponse(item.getCart(), roleName, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    @Transactional
    public CartResponse removeItem(Long itemId) {
        Long userId = currentUserContext.requireUserId();
        CartItem item = cartItemRepository.findByIdAndCartUserId(itemId, userId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm trong giỏ với id: " + itemId));
        Cart cart = item.getCart();
        cartItemRepository.delete(item);

        RoleName roleName = currentUserContext.requirePrincipal().getRoleName();
        return buildCartResponse(cart, roleName, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    @Transactional
    public void clearMyCart() {
        Long userId = currentUserContext.requireUserId();
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            return;
        }
        cartItemRepository.deleteByCartId(cart.getId());
    }

    @Transactional(readOnly = true)
    public CartResponse buildCartResponse(Cart cart, RoleName roleName, BigDecimal shippingFee, BigDecimal discountAmount) {
        if (cart == null || cart.getId() == null) {
            return emptyCartResponse(currentUserContext.requireUserId());
        }

        List<CartItem> items = cartItemRepository.findAllByCartId(cart.getId());
        if (items.isEmpty()) {
            return emptyCartResponse(cart.getUser().getId());
        }

        List<Long> productIds = items.stream().map(i -> i.getProduct().getId()).filter(Objects::nonNull).distinct().toList();
        Map<Long, String> primaryImages = resolvePrimaryImages(productIds);

        BigDecimal subtotal = BigDecimal.ZERO;
        BigDecimal totalQuantity = BigDecimal.ZERO;

        List<CartItemResponse> itemResponses = items.stream().map(item -> {
            BigDecimal qty = item.getQuantity() == null ? BigDecimal.ZERO : item.getQuantity();
            BigDecimal unitPrice = pricingService.resolveUnitPrice(item.getProduct(), qty, roleName);
            BigDecimal lineTotal = unitPrice.multiply(qty).setScale(2, RoundingMode.HALF_UP);
            return CartItemResponse.builder()
                    .id(item.getId())
                    .productId(item.getProduct().getId())
                    .productSku(item.getProduct().getSku())
                    .productName(item.getProduct().getName())
                    .productImageUrl(primaryImages.get(item.getProduct().getId()))
                    .uomId(item.getUom().getId())
                    .uomSymbol(item.getUom().getSymbol())
                    .quantity(qty)
                    .unitPrice(unitPrice)
                    .lineTotal(lineTotal)
                    .build();
        }).toList();

        for (CartItemResponse itemResponse : itemResponses) {
            subtotal = subtotal.add(itemResponse.getLineTotal());
            totalQuantity = totalQuantity.add(itemResponse.getQuantity());
        }

        BigDecimal normalizedShippingFee = normalizeMoney(shippingFee);
        BigDecimal normalizedDiscount = normalizeMoney(discountAmount);
        BigDecimal vatAmount = subtotal.multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalAmount = subtotal.add(vatAmount).add(normalizedShippingFee).subtract(normalizedDiscount)
                .setScale(2, RoundingMode.HALF_UP);

        return CartResponse.builder()
                .id(cart.getId())
                .userId(cart.getUser().getId())
                .totalQuantity(totalQuantity.setScale(2, RoundingMode.HALF_UP))
                .subtotal(subtotal.setScale(2, RoundingMode.HALF_UP))
                .vatAmount(vatAmount)
                .shippingFee(normalizedShippingFee)
                .discountAmount(normalizedDiscount)
                .totalAmount(totalAmount)
                .items(itemResponses)
                .build();
    }

    @Transactional(readOnly = true)
    public Cart findMyCartOrThrow() {
        Long userId = currentUserContext.requireUserId();
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new BadRequestException("Giỏ hàng đang trống"));
    }

    private Cart findOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng với id: " + userId));
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    private Uom resolveUom(Product product, Long requestedUomId) {
        if (requestedUomId == null) {
            if (product.getBaseUom() == null || product.getBaseUom().getId() == null) {
                throw new BadRequestException("Sản phẩm chưa có đơn vị tính mặc định");
            }
            return product.getBaseUom();
        }
        return uomRepository.findById(requestedUomId)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy đơn vị tính với id: " + requestedUomId));
    }

    private Map<Long, String> resolvePrimaryImages(List<Long> productIds) {
        if (productIds == null || productIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<ProductImage> images = productImageRepository.findByProductIdInOrderByProductIdAscSortOrderAsc(productIds);
        return images.stream()
                .collect(Collectors.toMap(
                        image -> image.getProduct().getId(),
                        ProductImage::getImageUrl,
                        (left, right) -> left
                ));
    }

    private CartResponse emptyCartResponse(Long userId) {
        return CartResponse.builder()
                .id(null)
                .userId(userId)
                .totalQuantity(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .subtotal(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .vatAmount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .shippingFee(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .discountAmount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .totalAmount(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP))
                .items(List.of())
                .build();
    }

    private BigDecimal normalizeMoney(BigDecimal value) {
        return value == null ? BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) : value.setScale(2, RoundingMode.HALF_UP);
    }
}
