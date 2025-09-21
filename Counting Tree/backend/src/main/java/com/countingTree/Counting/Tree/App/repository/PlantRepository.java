package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countingTree.Counting.Tree.App.model.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    
}
