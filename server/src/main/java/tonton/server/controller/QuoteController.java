package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.QuoteMapper;
import tonton.server.model.Quote;
import tonton.server.controller.request.QuoteRequest;
import tonton.server.controller.response.QuoteResponse;
import tonton.server.service.QuoteService;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping
    public ResponseEntity<List<QuoteResponse>> getAll() {
        return ResponseEntity.ok(quoteService.getAll().stream().map(QuoteMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuoteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(QuoteMapper.toResponse(quoteService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> create(@Valid @RequestBody QuoteRequest request) {
        Quote created = quoteService.create(QuoteMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(QuoteMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuoteResponse> update(@PathVariable Long id, @Valid @RequestBody QuoteRequest request) {
        Quote updated = quoteService.update(id, QuoteMapper.toEntity(request));
        return ResponseEntity.ok(QuoteMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        quoteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}