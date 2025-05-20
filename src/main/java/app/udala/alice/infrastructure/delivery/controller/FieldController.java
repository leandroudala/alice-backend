package app.udala.alice.infrastructure.delivery.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.udala.alice.application.port.GetFieldsUseCase;
import app.udala.alice.application.port.ManageFieldUseCase;
import app.udala.alice.domain.entity.Field;
import app.udala.alice.infrastructure.delivery.dto.FieldCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.FieldDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.FieldResponse;
import app.udala.alice.infrastructure.delivery.dto.FieldUpdateRequest;

@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    private final GetFieldsUseCase getUseCase;
    private final ManageFieldUseCase manageUseCase;

    public FieldController(GetFieldsUseCase fieldsUseCase, ManageFieldUseCase manageUseCase) {
        this.getUseCase = fieldsUseCase;
        this.manageUseCase = manageUseCase;
    }

    @GetMapping()
    public List<FieldResponse> getFields(@RequestParam String entityId) {
        List<FieldResponse> response = this.getUseCase.getFieldsByEntityId(entityId);
        return response;
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody FieldCreateRequest request, UriComponentsBuilder uriBuilder) {
        Field created = this.manageUseCase.create(request);
        URI uri = uriBuilder.path("/api/v1/fields/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FieldDetailsResponse> getField(@PathVariable String id) {
        FieldDetailsResponse response = this.getUseCase.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody FieldUpdateRequest request) {
        this.manageUseCase.update(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.manageUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
