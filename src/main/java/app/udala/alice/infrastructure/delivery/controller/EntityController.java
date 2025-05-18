package app.udala.alice.infrastructure.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.udala.alice.application.port.EntityManagementUseCase;
import app.udala.alice.infrastructure.delivery.dto.EntityCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.EntityResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody EntityCreateRequest request, UriComponentsBuilder uriBuilder) {
        EntityResponse response = this.managementUseCase.create(request);
        var uri = uriBuilder.path("/api/v1/entities/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
