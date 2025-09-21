package com.countingTree.Counting.Tree.App.service;

import java.util.List;

import com.countingTree.Counting.Tree.App.model.Alert;

public interface AlertService {

    void addAlert(Alert newAlert);

    Alert getAlertById(Long id);

    List<Alert> getAllAlerts();

    void deleteAlert(Long id);

    Alert updateAlert(Long id, Alert alert);

}
