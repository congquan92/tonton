package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tonton.server.controller.request.checkout.CheckoutRequest;
import tonton.server.controller.response.checkout.CheckoutResponse;
import tonton.server.service.CheckoutService;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponse> placeOrder(@Valid @RequestBody CheckoutRequest request) {
        return ResponseEntity.ok(checkoutService.placeOrder(request));
    }
}
