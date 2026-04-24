package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.UomMapper;
import tonton.server.model.Uom;
import tonton.server.controller.request.UomRequest;
import tonton.server.controller.response.UomResponse;
import tonton.server.service.UomService;

import java.util.List;

@RestController
@RequestMapping("/api/uoms")
@RequiredArgsConstructor
public class UomController {

    private final UomService uomService;

    @GetMapping
    public ResponseEntity<List<UomResponse>> getAll() {
        return ResponseEntity.ok(uomService.getAll().stream().map(UomMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UomResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(UomMapper.toResponse(uomService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<UomResponse> create(@Valid @RequestBody UomRequest request) {
        Uom created = uomService.create(UomMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UomMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UomResponse> update(@PathVariable Long id, @Valid @RequestBody UomRequest request) {
        Uom updated = uomService.update(id, UomMapper.toEntity(request));
        return ResponseEntity.ok(UomMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        uomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}