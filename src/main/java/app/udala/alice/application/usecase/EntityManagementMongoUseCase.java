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
import app.udala.alice.shared.exception.EntityDuplicatedException;
import app.udala.alice.shared.exception.EntityNotFoundException;

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
            throw new EntityDuplicatedException("Entity already exists");
        }
    }

    @Override
    public void update(EntityUpdateRequest request) {
        Entity entity = this.repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(request.getId()));
        
        if (!entity.getName().equalsIgnoreCase(request.getName())) {
            this.checkForDuplicatedEntity(entity.getBaseId(), request.getName());
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        this.repository.save(entity);
    }

    @Override
    public void delete(String id) {
        Entity entity = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
        this.repository.delete(entity);
    }

    @Override
    public EntityDetailedResponse getById(String id) {
        Entity entity = this.repository.findById(id)
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
