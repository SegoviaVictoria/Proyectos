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
        alertRepository.deleteById(id);
    }

    @Override
    public Alert updateAlert(Long id, Alert alert) {

        Alert alertToUpdate = alertRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Alert with ID " + id + " not found"));
        return alertRepository.save(alert);
    }


    // EXTRA METHODS

    private void validateAlert (Alert alert)

}
