package com.countingTree.Counting.Tree.App.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.countingTree.Counting.Tree.App.model.Alert;

public interface AlertRepository extends JpaRepository<Alert, Long> {

}