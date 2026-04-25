package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.RoleMapper;
import tonton.server.model.Role;
import tonton.server.controller.request.role.RoleRequest;
import tonton.server.controller.response.role.RoleResponse;
import tonton.server.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.getAll().stream().map(RoleMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(RoleMapper.toResponse(roleService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> create(@Valid @RequestBody RoleRequest request) {
        Role created = roleService.create(RoleMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(RoleMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleResponse> update(@PathVariable Long id, @Valid @RequestBody RoleRequest request) {
        Role updated = roleService.update(id, RoleMapper.toEntity(request));
        return ResponseEntity.ok(RoleMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}