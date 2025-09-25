package com.countingTree.Counting.Tree.App.controller;

import com.countingTree.Counting.Tree.App.model.Plant;
import com.countingTree.Counting.Tree.App.model.Coordinate;
import com.countingTree.Counting.Tree.App.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    @PostMapping
    public ResponseEntity<Void> addPlant(@RequestBody Plant plant) {
        plantService.addPlant(plant);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlant(@PathVariable Long id, @RequestBody Plant plant) {
        plantService.updatePlant(id, plant);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        plantService.deletePlant(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Plant> getPlant(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getPlant(id));
    }

    @GetMapping
    public ResponseEntity<List<Plant>> getAllPlants() {
        return ResponseEntity.ok(plantService.getAllPlants());
    }

    @PostMapping("/{id}/coordinate")
    public ResponseEntity<Void> addCoordinate(@PathVariable Long id, @RequestBody Coordinate coordinate) {
        plantService.addCoordinateForPlant(id, coordinate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/coordinate")
    public ResponseEntity<Coordinate> getCoordinate(@PathVariable Long id) {
        return ResponseEntity.ok(plantService.getCoordinateForPlant(id));
    }

    @PutMapping("/{id}/coordinate")
    public ResponseEntity<Void> updateCoordinate(@PathVariable Long id, @RequestBody Coordinate coordinate) {
        plantService.updateCoordinateForPlant(id, coordinate);
        return ResponseEntity.ok().build();
    }
}
