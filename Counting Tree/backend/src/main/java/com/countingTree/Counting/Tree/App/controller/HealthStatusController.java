package com.countingTree.Counting.Tree.App.controller;

import com.countingTree.Counting.Tree.App.model.HealthStatus;
import com.countingTree.Counting.Tree.App.service.HealthStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/health-statuses")
public class HealthStatusController {

    @Autowired
    private HealthStatusService healthStatusService;

    @GetMapping("/{id}")
    public ResponseEntity<HealthStatus> getHealthStatusById(@PathVariable Long id) {
        return ResponseEntity.ok(healthStatusService.getHealthStatusById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addHealthStatus(@RequestBody HealthStatus healthStatus) {
        healthStatusService.addHealthStatus(healthStatus);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateHealthStatus(@PathVariable Long id, @RequestBody HealthStatus healthStatus) {
        healthStatusService.updateHealthStatus(id, healthStatus);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthStatus(@PathVariable Long id) {
        healthStatusService.deleteHealthStatus(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<HealthStatus>> getAllHealthStatuses() {
        return ResponseEntity.ok(healthStatusService.getAllHealthStatuses());
    }
}
