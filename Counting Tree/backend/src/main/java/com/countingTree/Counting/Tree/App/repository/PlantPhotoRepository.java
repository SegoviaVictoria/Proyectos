package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countingTree.Counting.Tree.App.model.PlantPhoto;

public interface PlantPhotoRepository extends JpaRepository<PlantPhoto, Long> {
    
}
