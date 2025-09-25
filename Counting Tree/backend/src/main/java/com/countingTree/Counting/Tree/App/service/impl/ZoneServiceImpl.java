package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countingTree.Counting.Tree.App.model.Zone;
import com.countingTree.Counting.Tree.App.repository.ZoneRepository;
import com.countingTree.Counting.Tree.App.service.ZoneService;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Override
    public void addZone(Zone newZone) {
        validateZone(newZone);
        zoneRepository.save(newZone);
    }

    @Override
    public void deleteZone(Long zoneId) {
        Zone existingZone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new IllegalArgumentException("Zone with ID " + zoneId + " not found."));
        zoneRepository.deleteById(zoneId);
    }

    @Override
    public Zone getZoneById(Long zoneId) {
        return zoneRepository.findById(zoneId)
                .orElseThrow(() -> new IllegalArgumentException("Zone with ID " + zoneId + " not found."));
    }

    @Override
    public void updateZone(Long zoneId, Zone zone) {
        Zone existingZone = zoneRepository.findById(zoneId)
                .orElseThrow(() -> new IllegalArgumentException("Zone with ID " + zoneId + " not found."));
        validateZone(zone);
        existingZone.setName(zone.getName());
        existingZone.setDescription(zone.getDescription());
        existingZone.setCoordinates(zone.getCoordinates());
        zoneRepository.save(existingZone);
    }

    @Override
    public List<Zone> getAllZones() {
        return zoneRepository.findAll();
    }

    // EXTRA METHODS
    private void validateZone(Zone zone) {
        if (zone == null) {
            throw new IllegalArgumentException("Zone must not be null");
        }
        if (zone.getName() == null || zone.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Zone name must not be null or empty");
        }
        if (zone.getCoordinates() == null) {
            throw new IllegalArgumentException("Zone must have coordinates.");
        }
    }
}
