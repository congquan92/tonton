package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.CategoryMapper;
import tonton.server.model.Category;
import tonton.server.controller.request.CategoryRequest;
import tonton.server.controller.response.CategoryResponse;
import tonton.server.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(categoryService.getAll().stream().map(CategoryMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(CategoryMapper.toResponse(categoryService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        Category created = categoryService.create(CategoryMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        Category updated = categoryService.update(id, CategoryMapper.toEntity(request));
        return ResponseEntity.ok(CategoryMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}