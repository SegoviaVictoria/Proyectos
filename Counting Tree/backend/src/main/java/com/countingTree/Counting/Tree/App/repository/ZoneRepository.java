package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countingTree.Counting.Tree.App.model.Zone;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    
}
