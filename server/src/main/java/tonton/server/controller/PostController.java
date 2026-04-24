package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.PostMapper;
import tonton.server.model.Post;
import tonton.server.controller.request.PostRequest;
import tonton.server.controller.response.PostResponse;
import tonton.server.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAll() {
        return ResponseEntity.ok(postService.getAll().stream().map(PostMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(PostMapper.toResponse(postService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<PostResponse> create(@Valid @RequestBody PostRequest request) {
        Post created = postService.create(PostMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(PostMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable Long id, @Valid @RequestBody PostRequest request) {
        Post updated = postService.update(id, PostMapper.toEntity(request));
        return ResponseEntity.ok(PostMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}