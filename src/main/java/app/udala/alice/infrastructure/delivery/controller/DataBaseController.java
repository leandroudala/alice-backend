package app.udala.alice.infrastructure.delivery.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.udala.alice.application.port.CreateDataBaseUseCase;
import app.udala.alice.application.port.GetDataBaseUseCase;
import app.udala.alice.infrastructure.delivery.dto.DataBaseCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;

@RestController
@RequestMapping("/api/v1/databases")
public class DataBaseController {

    private final CreateDataBaseUseCase createDatabase;
    private final GetDataBaseUseCase getDataBase;

    public DataBaseController(CreateDataBaseUseCase createDatabase, GetDataBaseUseCase listDataBases) {
        this.createDatabase = createDatabase;
        this.getDataBase = listDataBases;
    }

    @GetMapping()
    public ResponseEntity<List<DataBaseResponse>> getDataBases() {
        return ResponseEntity.ok(this.getDataBase.getAll());
    }

    @PostMapping()
    public ResponseEntity<DataBaseResponse> create(@RequestBody DataBaseCreateRequest request) {
        DataBaseResponse response = this.createDatabase.create(request);
        URI uri = URI.create(String.format("/api/v1/databases/%s", response.getId()));
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataBaseResponse> getDataBase(@PathVariable String id) {
        return ResponseEntity.ok(this.getDataBase.getById(id));
    }

}
