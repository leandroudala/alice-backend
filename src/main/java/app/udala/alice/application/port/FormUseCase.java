package app.udala.alice.application.port;

import java.util.List;

import app.udala.alice.infrastructure.delivery.dto.DetailedFormResponse;
import app.udala.alice.infrastructure.delivery.dto.DocumentResponse;
import app.udala.alice.infrastructure.delivery.dto.FormResponse;

public interface FormUseCase {

    DetailedFormResponse getForm(String entityId);

    List<FormResponse> getForms();

    String insertAnswer(String entityId, String answerPayload);

    List<DocumentResponse> findAllDynamic(String entityId);

}
