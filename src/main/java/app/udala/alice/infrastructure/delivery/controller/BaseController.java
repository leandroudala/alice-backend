package app.udala.alice.infrastructure.delivery.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import app.udala.alice.application.port.BaseUseCase;
import app.udala.alice.application.port.GetBaseUseCase;
import app.udala.alice.application.port.ManageBaseUseCase;
import app.udala.alice.infrastructure.delivery.dto.BaseCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.BaseDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.BaseResponse;
import app.udala.alice.infrastructure.delivery.dto.BaseUpdateRequest;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/bases")
public class BaseController {

    private final BaseUseCase createBase;
    private final GetBaseUseCase getBase;
    private final ManageBaseUseCase manageBase;

    public BaseController(BaseUseCase createBase, GetBaseUseCase listBases,
            ManageBaseUseCase manageBase) {
        this.createBase = createBase;
        this.getBase = listBases;
        this.manageBase = manageBase;
    }

    @GetMapping()
    public ResponseEntity<List<BaseResponse>> getBases() {
        return ResponseEntity.ok(this.getBase.getAll());
    }

    @PostMapping()
    public ResponseEntity<BaseResponse> create(@RequestBody BaseCreateRequest request,
            UriComponentsBuilder uriBuilder) {
        BaseResponse response = this.createBase.create(request);

        URI uri = uriBuilder.path("/api/v1/bases/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseDetailsResponse> getBaseById(@PathVariable String id) {
        return ResponseEntity.ok(this.getBase.getById(id));
    }

    @PutMapping()
    public ResponseEntity<BaseResponse> update(@RequestBody BaseUpdateRequest request) {
        return ResponseEntity.ok(this.manageBase.updateBase(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.manageBase.deleteBase(id);
        return ResponseEntity.noContent().build();
    }

}
