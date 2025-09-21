package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.PlantPhoto;

public interface PlantPhotoService {

    PlantPhoto getPhotoForPlant(Long plantId);

    List<PlantPhoto> getAllPhotosForPlant(Long plantId);

    void updatePhotoForPlant(Long plantId, PlantPhoto newPhoto);

    void deletePhotoForPlant(Long plantId);

    void addPhotoForPlant(Long plantId, PlantPhoto photo);
}
