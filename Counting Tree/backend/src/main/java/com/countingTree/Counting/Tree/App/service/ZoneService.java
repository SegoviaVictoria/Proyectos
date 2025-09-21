package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.Zone;

public interface ZoneService {
    
    void addZone(Zone newZone);

    void deleteZone(Long zoneId);

    Zone getZoneById(Long zoneId);

    void updateZone(Long zoneId, Zone zone);

    List<Zone> getAllZones();
}
