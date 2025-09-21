package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.countingTree.Counting.Tree.App.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    
}
