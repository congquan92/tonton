package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.PriceTierMapper;
import tonton.server.model.PriceTier;
import tonton.server.controller.request.PriceTierRequest;
import tonton.server.controller.response.PriceTierResponse;
import tonton.server.service.PriceTierService;

import java.util.List;

@RestController
@RequestMapping("/api/price-tiers")
@RequiredArgsConstructor
public class PriceTierController {

    private final PriceTierService priceTierService;

    @GetMapping
    public ResponseEntity<List<PriceTierResponse>> getAll() {
        return ResponseEntity.ok(priceTierService.getAll().stream().map(PriceTierMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceTierResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(PriceTierMapper.toResponse(priceTierService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<PriceTierResponse> create(@Valid @RequestBody PriceTierRequest request) {
        PriceTier created = priceTierService.create(PriceTierMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(PriceTierMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PriceTierResponse> update(@PathVariable Long id, @Valid @RequestBody PriceTierRequest request) {
        PriceTier updated = priceTierService.update(id, PriceTierMapper.toEntity(request));
        return ResponseEntity.ok(PriceTierMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        priceTierService.delete(id);
        return ResponseEntity.noContent().build();
    }
}