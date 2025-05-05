package app.udala.alice.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import app.udala.alice.application.port.DataBaseRepository;
import app.udala.alice.application.port.GetDataBaseUseCase;
import app.udala.alice.domain.entity.DataBase;
import app.udala.alice.infrastructure.delivery.dto.DataBaseDetailsResponse;
import app.udala.alice.infrastructure.delivery.dto.DataBaseResponse;
import app.udala.alice.infrastructure.delivery.mapper.DataBaseInfrastructureMapper;
import app.udala.alice.infrastructure.persistence.entity.DataBaseDocument;
import app.udala.alice.infrastructure.persistence.mapper.DataBasePersistenceMapper;
import app.udala.alice.shared.converter.LocalDateTimeConverter;
import app.udala.alice.shared.exception.DataBaseNotFoundException;

public class GetDataBaseMongoUseCase implements GetDataBaseUseCase {

    private final DataBaseRepository dataBaseRepository;

    public GetDataBaseMongoUseCase(DataBaseRepository dataBaseRepository) {
        this.dataBaseRepository = dataBaseRepository;
    }

    @Override
    public List<DataBaseResponse> getAll() {
        return dataBaseRepository.findAllByDeletedAtIsNull().stream()
                .map(DataBasePersistenceMapper::toDomain)
                .map(DataBaseInfrastructureMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override

    public DataBaseDetailsResponse getById(String id) {
        DataBaseDocument document = dataBaseRepository.findById(id)
                .orElseThrow(() -> new DataBaseNotFoundException(id));

        DataBase dataBase = DataBasePersistenceMapper.toDomain(document);
        return toDetailsResponse(dataBase);
    }

    private static DataBaseDetailsResponse toDetailsResponse(DataBase dataBase) {
        return new DataBaseDetailsResponse(
                dataBase.getId(),
                dataBase.getName(),
                dataBase.getDescription(),
                LocalDateTimeConverter.convertToString(dataBase.getCreatedAt()),
                LocalDateTimeConverter.convertToString(dataBase.getUpdatedAt()),
                LocalDateTimeConverter.convertToString(dataBase.getDeletedAt()));
    }

}
