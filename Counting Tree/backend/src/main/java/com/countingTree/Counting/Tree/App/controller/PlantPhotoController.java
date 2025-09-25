package com.countingTree.Counting.Tree.App.controller;

import com.countingTree.Counting.Tree.App.model.PlantPhoto;
import com.countingTree.Counting.Tree.App.service.PlantPhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/plant-photos")
public class PlantPhotoController {

    @Autowired
    private PlantPhotoService plantPhotoService;

    @GetMapping("/{id}")
    public ResponseEntity<PlantPhoto> getPlantPhotoById(@PathVariable Long id) {
        return ResponseEntity.ok(plantPhotoService.getPlantPhotoById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addPlantPhoto(@RequestBody PlantPhoto plantPhoto) {
        plantPhotoService.addPlantPhoto(plantPhoto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlantPhoto(@PathVariable Long id, @RequestBody PlantPhoto plantPhoto) {
        plantPhotoService.updatePlantPhoto(id, plantPhoto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlantPhoto(@PathVariable Long id) {
        plantPhotoService.deletePlantPhoto(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PlantPhoto>> getAllPlantPhotos() {
        return ResponseEntity.ok(plantPhotoService.getAllPlantPhotos());
    }
}
