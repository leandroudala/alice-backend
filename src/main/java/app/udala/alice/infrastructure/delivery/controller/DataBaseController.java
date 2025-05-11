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

import app.udala.alice.application.port.CreateDataBaseUseCase;
import app.udala.alice.application.port.GetDataBaseUseCase;
import app.udala.alice.application.port.ManageDataBaseUseCase;
import app.udala.alice.infrastructure.delivery.dto.DataBaseCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.DataBaseDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;
import app.udala.alice.infrastructure.delivery.dto.DataBaseUpdateRequest;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/databases")
public class DataBaseController {

    private final CreateDataBaseUseCase createDatabase;
    private final GetDataBaseUseCase getDataBase;
    private final ManageDataBaseUseCase manageDataBase;

    public DataBaseController(CreateDataBaseUseCase createDatabase, GetDataBaseUseCase listDataBases,
            ManageDataBaseUseCase manageDataBase) {
        this.createDatabase = createDatabase;
        this.getDataBase = listDataBases;
        this.manageDataBase = manageDataBase;
    }

    @GetMapping()
    public ResponseEntity<List<DataBaseResponse>> getDataBases() {
        return ResponseEntity.ok(this.getDataBase.getAll());
    }

    @PostMapping()
    public ResponseEntity<DataBaseResponse> create(@RequestBody DataBaseCreateRequest request,
            UriComponentsBuilder uriBuilder) {
        DataBaseResponse response = this.createDatabase.create(request);

        URI uri = uriBuilder.path("/api/v1/databases/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataBaseDetailsResponse> getDataBase(@PathVariable String id) {
        return ResponseEntity.ok(this.getDataBase.getById(id));
    }

    @PutMapping()
    public ResponseEntity<DataBaseResponse> update(@RequestBody DataBaseUpdateRequest request) {
        return ResponseEntity.ok(this.manageDataBase.updateDataBase(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        this.manageDataBase.deleteDataBase(id);
        return ResponseEntity.noContent().build();
    }

}
