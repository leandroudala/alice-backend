package app.udala.alice.application.usecase;

import java.util.List;

import app.udala.alice.application.port.EntityManagementUseCase;
import app.udala.alice.application.port.EntityRepository;
import app.udala.alice.domain.entity.Entity;
import app.udala.alice.infrastructure.delivery.dto.EntityCreateRequest;
import app.udala.alice.infrastructure.delivery.dto.EntityDetailedResponse;
import app.udala.alice.infrastructure.delivery.dto.EntityResponse;
import app.udala.alice.infrastructure.delivery.dto.EntityUpdateRequest;
import app.udala.alice.infrastructure.delivery.mapper.EntityDeliveryMapper;

public class EntityManagementMongoUseCase implements EntityManagementUseCase {

    private final EntityRepository repository;

    public EntityManagementMongoUseCase(EntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public EntityResponse create(EntityCreateRequest request) {
        this.checkForDuplicatedEntity(request.getBaseId(), request.getName());
        Entity entity = EntityDeliveryMapper.toDomain(request);
        var saved = this.repository.save(entity);
        return EntityDeliveryMapper.toResponse(saved);
    }

    private void checkForDuplicatedEntity(String baseId, String name) {
        long total = this.repository.countByNameAndBaseIdAndDeletedAtIsNull(name, baseId);
        if (total > 0) {
            throw new IllegalArgumentException("Entity already exists");
        }
    }

    @Override
    public void update(EntityUpdateRequest request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public EntityDetailedResponse getById(String id) {
        Entity entity = this.repository.findByBaseId(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity not found"));
        
        return EntityDeliveryMapper.toDetailedResponse(entity);
    }

    @Override
    public List<EntityResponse> getAllByBaseId(String baseId) {
        return this.repository.findAllByBaseIdAndDeletedAtIsNull(baseId).stream()
                .map(EntityDeliveryMapper::toResponse)
                .toList();
    }
    
}
