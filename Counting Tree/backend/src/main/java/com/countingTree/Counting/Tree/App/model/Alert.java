package com.countingTree.Counting.Tree.App.model;

import org.springframework.format.annotation.DateTimeFormat;

public class Alert {
    
    private Long alertId;
    private String type;
    private String message;
    private DateTimeFormat creationDate;
    private AlertStatus status;

    private Plant plant;
    private User user;

    public Alert(Long alertId, String type, String message, DateTimeFormat creationDate, AlertStatus status, Plant plant, User user) {
        this.alertId = alertId;
        this.type = type;
        this.message = message;
        this.creationDate = creationDate;
        this.status = status;
        this.plant = plant;
        this.user = user;
    }

    public Alert() {
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DateTimeFormat getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTimeFormat creationDate) {
        this.creationDate = creationDate;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
}
