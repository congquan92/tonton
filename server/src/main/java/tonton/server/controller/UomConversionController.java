package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.UomConversionMapper;
import tonton.server.model.UomConversion;
import tonton.server.controller.request.UomConversionRequest;
import tonton.server.controller.response.UomConversionResponse;
import tonton.server.service.UomConversionService;

import java.util.List;

@RestController
@RequestMapping("/api/uom-conversions")
@RequiredArgsConstructor
public class UomConversionController {

    private final UomConversionService uomConversionService;

    @GetMapping
    public ResponseEntity<List<UomConversionResponse>> getAll() {
        return ResponseEntity.ok(uomConversionService.getAll().stream().map(UomConversionMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UomConversionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(UomConversionMapper.toResponse(uomConversionService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<UomConversionResponse> create(@Valid @RequestBody UomConversionRequest request) {
        UomConversion created = uomConversionService.create(UomConversionMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UomConversionMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UomConversionResponse> update(@PathVariable Long id, @Valid @RequestBody UomConversionRequest request) {
        UomConversion updated = uomConversionService.update(id, UomConversionMapper.toEntity(request));
        return ResponseEntity.ok(UomConversionMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        uomConversionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}