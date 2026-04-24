package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.ProductImageMapper;
import tonton.server.model.ProductImage;
import tonton.server.controller.request.ProductImageRequest;
import tonton.server.controller.response.ProductImageResponse;
import tonton.server.service.ProductImageService;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @GetMapping
    public ResponseEntity<List<ProductImageResponse>> getAll() {
        return ResponseEntity.ok(productImageService.getAll().stream().map(ProductImageMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductImageResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ProductImageMapper.toResponse(productImageService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ProductImageResponse> create(@Valid @RequestBody ProductImageRequest request) {
        ProductImage created = productImageService.create(ProductImageMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductImageMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductImageResponse> update(@PathVariable Long id, @Valid @RequestBody ProductImageRequest request) {
        ProductImage updated = productImageService.update(id, ProductImageMapper.toEntity(request));
        return ResponseEntity.ok(ProductImageMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productImageService.delete(id);
        return ResponseEntity.noContent().build();
    }
}