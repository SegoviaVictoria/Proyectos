package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.countingTree.Counting.Tree.App.model.PlantPhoto;
import com.countingTree.Counting.Tree.App.repository.PlantPhotoRepository;
import com.countingTree.Counting.Tree.App.service.PlantPhotoService;

public class PlantPhotoServiceImpl implements PlantPhotoService {
    
    @Autowired
    private PlantPhotoRepository plantPhotoRepository;

    @Override
    public PlantPhoto getPhotoForPlant(Long plantId) {
        PlantPhoto photoSearched = plantPhotoRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Photo for Plant with ID " + plantId + " not found."));
        return photoSearched;
    }

    @Override
    public List<PlantPhoto> getAllPhotosForPlant(Long plantId) {
        return plantPhotoRepository.findAll().stream()
                .filter(photo -> photo.getPlant() != null && photo.getPlant().getPlantId().equals(plantId))
                .toList();
    }

    @Override
    public void updatePhotoForPlant(Long plantId, PlantPhoto newPhoto) {
        PlantPhoto existingPhoto = plantPhotoRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Photo for Plant with ID " + plantId + " not found."));

        existingPhoto.setUrl(newPhoto.getUrl());
        existingPhoto.setDateTaken(newPhoto.getDateTaken());

        plantPhotoRepository.save(existingPhoto);
    }

    @Override
    public void deletePhotoForPlant(Long plantId) {
        plantPhotoRepository.deleteById(plantId);
    }

    @Override
    public void addPhotoForPlant(Long plantId, PlantPhoto photo) {
        validatePhoto(photo);
        plantPhotoRepository.save(photo);
    }

    // EXTRA METHODS
    private void validatePhoto(PlantPhoto newPhoto) {
        if (newPhoto.getUrl() == null || newPhoto.getUrl().trim().isEmpty()) {
            throw new IllegalArgumentException("Photo URL cannot be null or empty");
        }
        if (newPhoto.getDateTaken() == null) {
            throw new IllegalArgumentException("Photo date cannot be null");
        }
        if (newPhoto.getPlant() == null) {
            throw new IllegalArgumentException("Photo must be associated with a plant");
        }
    }
       
}
