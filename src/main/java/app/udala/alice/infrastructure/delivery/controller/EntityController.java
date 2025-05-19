package app.udala.alice.infrastructure.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.udala.alice.application.port.EntityManagementUseCase;
import app.udala.alice.infrastructure.delivery.dto.EntityCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.EntityDetailedResponse;
import app.udala.alice.infrastructure.delivery.dto.EntityResponse;
import app.udala.alice.infrastructure.delivery.dto.EntityUpdateRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/entities")
public class EntityController {
    private final EntityManagementUseCase managementUseCase;

    public EntityController(EntityManagementUseCase managementUseCase) {
        this.managementUseCase = managementUseCase;
    }

    @GetMapping
    public List<EntityResponse> getEntities(@RequestParam(required = true) String baseId) {
        return this.managementUseCase.getAllByBaseId(baseId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityDetailedResponse> getEntityById(@PathVariable("id") String id) {
        return ResponseEntity.ok(this.managementUseCase.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody EntityCreateRequest request, UriComponentsBuilder uriBuilder) {
        EntityResponse response = this.managementUseCase.create(request);
        var uri = uriBuilder.path("/api/v1/entities/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody EntityUpdateRequest entity) {
        this.managementUseCase.update(entity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.managementUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }

}
