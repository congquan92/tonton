package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.OrderMapper;
import tonton.server.model.Order;
import tonton.server.controller.request.order.OrderRequest;
import tonton.server.controller.response.order.OrderResponse;
import tonton.server.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAll().stream().map(OrderMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(OrderMapper.toResponse(orderService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        Order created = orderService.create(OrderMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @Valid @RequestBody OrderRequest request) {
        Order updated = orderService.update(id, OrderMapper.toEntity(request));
        return ResponseEntity.ok(OrderMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}