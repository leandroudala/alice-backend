package app.udala.alice.infrastructure.delivery.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.udala.alice.application.port.GetFieldsUseCase;
import app.udala.alice.infrastructure.delivery.dto.FieldResponse;


@RestController
@RequestMapping("/api/v1/fields")
public class FieldController {

    private final GetFieldsUseCase fieldsUseCase;

    public FieldController(GetFieldsUseCase fieldsUseCase) {
        this.fieldsUseCase = fieldsUseCase;
    }

    @GetMapping()
    public List<FieldResponse> getFields(@RequestParam String databaseId) {
        List<FieldResponse> response = this.fieldsUseCase.getFieldsById(databaseId);
        return response;
    }
}
