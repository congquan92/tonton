package tonton.server.service;

import tonton.server.controller.request.checkout.CheckoutRequest;
import tonton.server.controller.response.checkout.CheckoutResponse;

public interface CheckoutService {
    CheckoutResponse placeOrder(CheckoutRequest request);
}
