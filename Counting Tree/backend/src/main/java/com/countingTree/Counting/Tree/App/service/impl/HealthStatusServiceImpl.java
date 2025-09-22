package com.countingTree.Counting.Tree.App.service.impl;

import com.countingTree.Counting.Tree.App.model.HealthStatus;
import com.countingTree.Counting.Tree.App.repository.HealthStatusRepository;
import com.countingTree.Counting.Tree.App.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HealthStatusServiceImpl implements HealthStatusService {

    @Autowired
	private HealthStatusRepository healthStatusRepository;

	@Override
	public void addHealthStatus(HealthStatus newHealthStatus) {
        validateNewHealthStatus(newHealthStatus);
		healthStatusRepository.save(newHealthStatus);
	}

	@Override
	public HealthStatus getHealthStatus(Long healthStatusId) {
        HealthStatus healthStatusSearched = healthStatusRepository.findById(healthStatusId)
                .orElseThrow(() -> new EntityNotFoundException("Health Status with ID " + healthStatusId + " not found."));
		return healthStatusSearched;
	}

	@Override
	public void updateHealthStatus(Long healthStatusId, HealthStatus healthStatus) {
		HealthStatus existing = healthStatusRepository.findById(healthStatusId)
                .orElseThrow(() -> new EntityNotFoundException("Health Status with ID " + healthStatusId + " not found."));
            
		existing.setName(healthStatus.getName());
		existing.setDescription(healthStatus.getDescription());
		healthStatusRepository.save(existing);
	}

	@Override
	public void deleteHealthStatus(Long healthStatusId) {
        HealthStatus existing = healthStatusRepository.findById(healthStatusId)
                .orElseThrow(() -> new EntityNotFoundException("Health Status with ID " + healthStatusId + " not found."));
        
        if (!existing.getPlants().isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This Health Status has associated plants and cannot be erased.")
        }
        
		healthStatusRepository.deleteById(healthStatusId);
	}

	@Override
	public List<HealthStatus> getAllHealthStatus() {
		return healthStatusRepository.findAll();
	}

    // EXTRA METHODS

    private void validateNewHealthStatus(HealthStatus newHealthStatus) {
        if (newHealthStatus.getName() == null || newHealthStatus.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Health status name cannot be null or empty");
        }
        if (healthStatusRepository.existsByName(newHealthStatus.getName())) {
            throw new IllegalArgumentException("Health status with the same name already exists");
        }
    }
}
