package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.PostCategoryMapper;
import tonton.server.model.PostCategory;
import tonton.server.controller.request.post.PostCategoryRequest;
import tonton.server.controller.response.post.PostCategoryResponse;
import tonton.server.service.PostCategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/post-categories")
@RequiredArgsConstructor
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    @GetMapping
    public ResponseEntity<List<PostCategoryResponse>> getAll() {
        return ResponseEntity.ok(postCategoryService.getAll().stream().map(PostCategoryMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostCategoryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(PostCategoryMapper.toResponse(postCategoryService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<PostCategoryResponse> create(@Valid @RequestBody PostCategoryRequest request) {
        PostCategory created = postCategoryService.create(PostCategoryMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(PostCategoryMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostCategoryResponse> update(@PathVariable Long id, @Valid @RequestBody PostCategoryRequest request) {
        PostCategory updated = postCategoryService.update(id, PostCategoryMapper.toEntity(request));
        return ResponseEntity.ok(PostCategoryMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}