package app.udala.alice.infrastructure.delivery.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.udala.alice.application.port.FormUseCase;
import app.udala.alice.infrastructure.delivery.dto.DetailedFormResponse;
import app.udala.alice.infrastructure.delivery.dto.FormResponse;

@RequestMapping("/api/v1/forms")
@RestController
public class FormController {

    private final FormUseCase usecase;

    public FormController(FormUseCase usecase) {
        this.usecase = usecase;
    }
    
    @GetMapping
    public List<FormResponse> getForms() {
        return this.usecase.getForms();
    }

    @GetMapping("/{entityId}")
    public ResponseEntity<DetailedFormResponse> getForm(@PathVariable String entityId) {
        DetailedFormResponse response = this.usecase.getForm(entityId);
        return ResponseEntity.ok(response);
    }
}
