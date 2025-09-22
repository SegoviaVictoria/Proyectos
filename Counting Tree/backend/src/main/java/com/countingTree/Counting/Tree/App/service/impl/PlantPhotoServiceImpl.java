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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPhotoForPlant'");
    }

    @Override
    public List<PlantPhoto> getAllPhotosForPlant(Long plantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPhotosForPlant'");
    }

    @Override
    public void updatePhotoForPlant(Long plantId, PlantPhoto newPhoto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePhotoForPlant'");
    }

    @Override
    public void deletePhotoForPlant(Long plantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePhotoForPlant'");
    }

    @Override
    public void addPhotoForPlant(Long plantId, PlantPhoto photo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addPhotoForPlant'");
    }

    // EXTRA METHODS
    
}
