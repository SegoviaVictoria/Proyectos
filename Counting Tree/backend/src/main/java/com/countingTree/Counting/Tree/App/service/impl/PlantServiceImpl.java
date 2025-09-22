package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.Plant;
import com.countingTree.Counting.Tree.App.service.PlantService;

public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public void addPlant(Long plantId, Plant newPlant) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPlant'");
    }

    @Override
    public void updatePlant(Long plantId, Plant newPlant) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePlant'");
    }

    @Override
    public void deletePlant(Long plantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePlant'");
    }

    @Override
    public String getPlant(Long plantId) {
        Plant existingPlant = plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Plant with ID " + plantId + " not found."));
        return existingPlant.toString();
    }

    @Override
    public List<Plant> getAllPlants() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPlants'");
    }

    @Override
    public List<Plant> getPlantsByUserId(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPlantsByUserId'");
    }
    
}
