package com.countingTree.Counting.Tree.App.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

public class Log {

    private Long logId;
    private String actionType;
    private DateTimeFormat actionDate;

    private User performedBy;
    private Set<Plant> relatedPlants = new HashSet<>();
    private Set<Alert> relatedAlerts = new HashSet<>();
    
    public Log(Long logId, String actionType, DateTimeFormat actionDate, User performedBy) {
        this.logId = logId;
        this.actionType = actionType;
        this.actionDate = actionDate;
        this.performedBy = performedBy;
    }

    public Log() {
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public DateTimeFormat getActionDate() {
        return actionDate;
    }

    public void setActionDate(DateTimeFormat actionDate) {
        this.actionDate = actionDate;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }

    public Set<Plant> getRelatedPlants() {
        return relatedPlants;
    }

    public void setRelatedPlants(Set<Plant> relatedPlants) {
        this.relatedPlants = relatedPlants;
    }

    public Set<Alert> getRelatedAlerts() {
        return relatedAlerts;
    }

    public void setRelatedAlerts(Set<Alert> relatedAlerts) {
        this.relatedAlerts = relatedAlerts;
    }
 
}
