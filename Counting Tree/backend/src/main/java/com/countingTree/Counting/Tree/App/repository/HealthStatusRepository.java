package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countingTree.Counting.Tree.App.model.HealthStatus;

public interface HealthStatusRepository extends JpaRepository<HealthStatus, Long> {
    
}
