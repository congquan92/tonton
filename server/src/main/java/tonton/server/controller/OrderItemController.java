package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.OrderItemMapper;
import tonton.server.model.OrderItem;
import tonton.server.controller.request.OrderItemRequest;
import tonton.server.controller.response.OrderItemResponse;
import tonton.server.service.OrderItemService;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<List<OrderItemResponse>> getAll() {
        return ResponseEntity.ok(orderItemService.getAll().stream().map(OrderItemMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(OrderItemMapper.toResponse(orderItemService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderItemResponse> create(@Valid @RequestBody OrderItemRequest request) {
        OrderItem created = orderItemService.create(OrderItemMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderItemMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponse> update(@PathVariable Long id, @Valid @RequestBody OrderItemRequest request) {
        OrderItem updated = orderItemService.update(id, OrderItemMapper.toEntity(request));
        return ResponseEntity.ok(OrderItemMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}