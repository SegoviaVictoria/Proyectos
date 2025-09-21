package com.countingTree.Counting.Tree.App.service;

import com.countingTree.Counting.Tree.App.model.Coordinate;

public interface CoordinateService {

    Coordinate getCoordinatesForPlant(Long plantId);

    void updateCoordinatesForPlant(Long plantId, Coordinate newCoordinates);

    void deleteCoordinatesForPlant(Long plantId);

    void addCoordinatesForPlant(Coordinate newCoordinates);

}
