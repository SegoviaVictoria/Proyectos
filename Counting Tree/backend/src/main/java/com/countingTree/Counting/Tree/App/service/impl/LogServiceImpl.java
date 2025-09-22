package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.countingTree.Counting.Tree.App.model.Log;
import com.countingTree.Counting.Tree.App.repository.LogRepository;
import com.countingTree.Counting.Tree.App.service.LogService;

public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public void addLog(Log log) {
        validateLog(log);
        logRepository.save(log);
    }

    @Override
    public Log getLogById(Long logId) {
        Log existingLog = logRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Log with ID " + logId + " not found."));
        return existingLog;
    }

    @Override
    public void updateLog(Long logId, Log log) {
        Log existingLog = logRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Log with ID " + logId + " not found."));
        validateLog(log);

        existingLog.setActionType(log.getActionType());
        existingLog.setActionDate(log.getActionDate());
        existingLog.setPerformedBy(log.getPerformedBy());
        existingLog.setRelatedPlant(log.getRelatedPlant());
        existingLog.setRelatedAlert(log.getRelatedAlert());

        logRepository.save(existingLog);
    }

    @Override
    public void deleteLog(Long logId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Log> getAllLogsByUserId(Long userId) {
        return logRepository.findAll().stream()
                .filter(log -> log.getPerformedBy() != null && log.getPerformedBy().getUserId().equals(userId))
                .toList();
    }

    // EXTRA METHODS

    private void validateLog(Log newLog) {
        if (newLog.getActionType() == null || newLog.getActionType().trim().isEmpty()) {
            throw new IllegalArgumentException("Action type cannot be null or empty");
        }
        if (newLog.getActionDate() == null) {
            throw new IllegalArgumentException("Action date cannot be null");
        }
        if (newLog.getPerformedBy() == null) {
            throw new IllegalArgumentException("Performed by user cannot be null");
        }
        if (newLog.getRelatedPlant() == null && newLog.getRelatedAlert() == null) {
            throw new IllegalArgumentException("Log must be associated with either a plant or an alert");
        }
    }
}
