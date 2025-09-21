package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.countingTree.Counting.Tree.App.model.Alert;
import com.countingTree.Counting.Tree.App.repository.AlertRepository;
import com.countingTree.Counting.Tree.App.service.AlertService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AlertServiceImpl implements AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Override
    public void addAlert(Alert newAlert) {
        validateNewAlert(newAlert);
        alertRepository.save(newAlert);
    }

    @Override
    public Alert getAlertById(Long id) {
        Alert alertSearched = alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert with ID " + id + " not found"));
        return alertSearched;
    }

    @Override
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Override
    public void deleteAlert(Long id) {
        Alert alertSearched = alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert with ID " + id + " not found"));
        if (!alertSearched.getPlants().isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Alert has associated plants.")
        }
        alertRepository.deleteById(id);
    }

    @Override
    public Alert updateAlert(Long id, Alert alert) {

        Alert alertToUpdate = alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert with ID " + id + " not found"));

        alertToUpdate.setType(alert.getType());
        alertToUpdate.setMessage(alert.getMessage());
        alertToUpdate.setStatus(alert.getStatus());
        alertToUpdate.setPlants(alert.getPlants());
        alertToUpdate.setResolver(alert.getResolver());
        alertRepository.save(alertToUpdate);

        return alertToUpdate;
    }

    // EXTRA METHODS

    public void validateNewAlert (Alert alert) {

        if (alert.getAlertId() != null) {
            throw new IllegalArgumentException("New alert cannot have an ID");
        }
        if (alert.getType() == null || alert.getType().isEmpty()) {
            throw new IllegalArgumentException("Alert type cannot be null or empty");
        }
        if (alert.getMessage() == null || alert.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Alert message cannot be null or empty");
        }
        if (alert.getCreationDate() == null) {
            throw new IllegalArgumentException("Alert creation date cannot be null");
        }
        if (alert.getStatus() == null) {
            throw new IllegalArgumentException("Alert status cannot be null or empty");
        }
        if (alert.getCreator() == null) {
            throw new IllegalArgumentException("Alert creator cannot be null");
        }

    }

}
