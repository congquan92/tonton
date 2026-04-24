package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.QuoteItemMapper;
import tonton.server.model.QuoteItem;
import tonton.server.controller.request.QuoteItemRequest;
import tonton.server.controller.response.QuoteItemResponse;
import tonton.server.service.QuoteItemService;

import java.util.List;

@RestController
@RequestMapping("/api/quote-items")
@RequiredArgsConstructor
public class QuoteItemController {

    private final QuoteItemService quoteItemService;

    @GetMapping
    public ResponseEntity<List<QuoteItemResponse>> getAll() {
        return ResponseEntity.ok(quoteItemService.getAll().stream().map(QuoteItemMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteItemResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(QuoteItemMapper.toResponse(quoteItemService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<QuoteItemResponse> create(@Valid @RequestBody QuoteItemRequest request) {
        QuoteItem created = quoteItemService.create(QuoteItemMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(QuoteItemMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteItemResponse> update(@PathVariable Long id, @Valid @RequestBody QuoteItemRequest request) {
        QuoteItem updated = quoteItemService.update(id, QuoteItemMapper.toEntity(request));
        return ResponseEntity.ok(QuoteItemMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quoteItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}