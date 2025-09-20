package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.countingTree.Counting.Tree.App.model.Coordinate;

public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
    
}
