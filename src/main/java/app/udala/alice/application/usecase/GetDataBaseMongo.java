package app.udala.alice.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.application.port.DataBaseRepository;
import app.udala.alice.application.port.GetDataBaseUseCase;
import app.udala.alice.domain.entity.DataBase;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;
import app.udala.alice.infrastructure.delivery.mapper.DataBaseInfrastructureMapper;
import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;
import app.udala.alice.infrastructure.persistence.mapper.DataBasePersistenceMapper;
import app.udala.alice.shared.exception.DataBaseNotFoundException;

public class GetDataBaseMongo implements GetDataBaseUseCase {

    private final DataBaseRepository dataBaseRepository;

    public GetDataBaseMongo(DataBaseRepository dataBaseRepository) {
        this.dataBaseRepository = dataBaseRepository;
    }

    @Override
    public List<DataBaseResponse> getAll() {
        return dataBaseRepository.findAll().stream()
                .map(DataBasePersistenceMapper::toDomain)
                .map(DataBaseInfrastructureMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DataBaseResponse getById(String id) {
        DataBaseDocument document = dataBaseRepository.findById(id)
                .orElseThrow(() -> new DataBaseNotFoundException(id));

        DataBase dataBase = DataBasePersistenceMapper.toDomain(document);
        return DataBaseInfrastructureMapper.toResponse(dataBase);
    }

}
