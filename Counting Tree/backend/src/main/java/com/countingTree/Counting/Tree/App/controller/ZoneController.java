package com.countingTree.Counting.Tree.App.controller;

import com.countingTree.Counting.Tree.App.model.Zone;
import com.countingTree.Counting.Tree.App.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @GetMapping("/{id}")
    public ResponseEntity<Zone> getZoneById(@PathVariable Long id) {
        return ResponseEntity.ok(zoneService.getZoneById(id));
    }

    @PostMapping
    public ResponseEntity<Void> addZone(@RequestBody Zone zone) {
        zoneService.addZone(zone);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateZone(@PathVariable Long id, @RequestBody Zone zone) {
        zoneService.updateZone(id, zone);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Zone>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }
}
