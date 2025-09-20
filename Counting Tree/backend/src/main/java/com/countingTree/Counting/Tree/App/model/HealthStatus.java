package com.countingTree.Counting.Tree.App.model;

public class HealthStatus {
    
    private Long statusId;
    private String name;
    private String description;

    public HealthStatus(Long statusId, String name, String description) {
        this.statusId = statusId;
        this.name = name;
        this.description = description;
    }

    public HealthStatus() {
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
