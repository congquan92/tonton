package tonton.server.service;

import tonton.server.controller.request.cart.CartItemUpsertRequest;
import tonton.server.controller.response.cart.CartResponse;

import java.math.BigDecimal;

public interface CartService {
    CartResponse getMyCart();

    CartResponse addItem(CartItemUpsertRequest request);

    CartResponse updateItemQuantity(Long itemId, BigDecimal quantity);

    CartResponse removeItem(Long itemId);

    void clearMyCart();
}
