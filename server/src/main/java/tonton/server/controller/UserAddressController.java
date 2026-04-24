package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tonton.server.mapper.UserAddressMapper;
import tonton.server.model.UserAddress;
import tonton.server.controller.request.UserAddressRequest;
import tonton.server.controller.response.UserAddressResponse;
import tonton.server.service.UserAddressService;

import java.util.List;

@RestController
@RequestMapping("/api/user-addresses")
@RequiredArgsConstructor
public class UserAddressController {

    private final UserAddressService userAddressService;

    @GetMapping
    public ResponseEntity<List<UserAddressResponse>> getAll() {
        return ResponseEntity.ok(userAddressService.getAll().stream().map(UserAddressMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAddressResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(UserAddressMapper.toResponse(userAddressService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<UserAddressResponse> create(@Valid @RequestBody UserAddressRequest request) {
        UserAddress created = userAddressService.create(UserAddressMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserAddressMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAddressResponse> update(@PathVariable Long id, @Valid @RequestBody UserAddressRequest request) {
        UserAddress updated = userAddressService.update(id, UserAddressMapper.toEntity(request));
        return ResponseEntity.ok(UserAddressMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userAddressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}