package tonton.server.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tonton.server.controller.request.servicefeature.ServiceFeatureRequest;
import tonton.server.controller.response.servicefeature.ServiceFeatureResponse;
import tonton.server.mapper.ServiceFeatureMapper;
import tonton.server.model.ServiceFeature;
import tonton.server.service.ServiceFeatureService;

import java.util.List;

@RestController
@RequestMapping("/api/service-features")
@RequiredArgsConstructor
public class ServiceFeatureController {

    private final ServiceFeatureService serviceFeatureService;

    @GetMapping
    public ResponseEntity<List<ServiceFeatureResponse>> getAll() {
        return ResponseEntity.ok(serviceFeatureService.getAll().stream().map(ServiceFeatureMapper::toResponse).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceFeatureResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ServiceFeatureMapper.toResponse(serviceFeatureService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ServiceFeatureResponse> create(@Valid @RequestBody ServiceFeatureRequest request) {
        ServiceFeature created = serviceFeatureService.create(ServiceFeatureMapper.toEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(ServiceFeatureMapper.toResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceFeatureResponse> update(@PathVariable Long id, @Valid @RequestBody ServiceFeatureRequest request) {
        ServiceFeature updated = serviceFeatureService.update(id, ServiceFeatureMapper.toEntity(request));
        return ResponseEntity.ok(ServiceFeatureMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceFeatureService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
