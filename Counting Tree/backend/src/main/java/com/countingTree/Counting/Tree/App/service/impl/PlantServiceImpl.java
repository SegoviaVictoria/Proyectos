package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countingTree.Counting.Tree.App.model.Coordinate;
import com.countingTree.Counting.Tree.App.model.Plant;
import com.countingTree.Counting.Tree.App.repository.PlantRepository;
import com.countingTree.Counting.Tree.App.service.PlantService;

@Service
public class PlantServiceImpl implements PlantService {

    @Autowired
    private PlantRepository plantRepository;

    @Override
    public void addPlant(Plant newPlant) {
        // TODO - Validar los datos del nuevo árbol si es necesario !!!
        plantRepository.save(newPlant);
    }

    @Override
    public void updatePlant(Long plantId, Plant newPlant) {
        Plant existingPlant = plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Plant with ID " + plantId + " not found."));

        existingPlant.setMainPhoto(newPlant.getMainPhoto());
        existingPlant.setLocation(newPlant.getLocation());
        existingPlant.setDatePlanted(newPlant.getDatePlanted());
        existingPlant.setSpecies(newPlant.getSpecies());
        existingPlant.setOwner(newPlant.getOwner());
        existingPlant.setPhotos(newPlant.getPhotos());
        existingPlant.setHealthStatus(newPlant.getHealthStatus());
        existingPlant.setVerificationStatus(newPlant.getVerificationStatus());
        existingPlant.setAlerts(newPlant.getAlerts());
        existingPlant.setComments(newPlant.getComments());
        existingPlant.setZone(newPlant.getZone());

        plantRepository.save(existingPlant);
    }

    @Override
    public void deletePlant(Long plantId) {
        // TODO - plantRepository.deleteById(plantId);
    }

    @Override
    public Plant getPlant(Long plantId) {
        Plant existingPlant = plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Plant with ID " + plantId + " not found."));
        return existingPlant;
    }

    @Override
    public List<Plant> getAllPlants() {
        return plantRepository.findAll();
    }

    @Override
    public void addCoordinateForPlant(Long plantId, Coordinate coordinates) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Plant with ID " + plantId + " not found."));
        plant.setLocation(coordinates);
        plantRepository.save(plant);
    }

    // @Override
    // public void deleteCoordinateForPlant(Long plantId) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'deleteCoordinateForPlant'");
    // }

    @Override
    public Coordinate getCoordinateForPlant(Long plantId) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Plant with ID " + plantId + " not found."));

        Coordinate coordinates = plant.getLocation();
        if (coordinates == null) {
            throw new IllegalArgumentException("No coordinates found for plant with ID " + plantId);
        }
        return coordinates;
    }

    @Override
    public void updateCoordinateForPlant(Long plantId, Coordinate newCoordinate) {
        Plant plant = plantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Plant with ID " + plantId + " not found."));
        plant.setLocation(newCoordinate);
        plantRepository.save(plant);
    }

}
