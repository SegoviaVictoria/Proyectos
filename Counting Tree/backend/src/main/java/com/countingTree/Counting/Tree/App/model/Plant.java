package com.countingTree.Counting.Tree.App.model;

import org.springframework.format.annotation.DateTimeFormat;

public class Plant {

    private Long plantId;
    private String mainPhoto;
    private Coordinate location;
    private DateTimeFormat datePlanted;
    private VerificationStatus verificationStatus;
    private HealthStatus healthStatus;

    public Plant(Long plantId, String mainPhoto, Coordinate location, DateTimeFormat datePlanted, VerificationStatus verificationStatus, HealthStatus healthStatus) {
        this.plantId = plantId;
        this.mainPhoto = mainPhoto;
        this.location = location;
        this.datePlanted = datePlanted;
        this.verificationStatus = verificationStatus;
        this.healthStatus = healthStatus;
    }

    public Plant() {
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location
    }

    public DateTimeFormat getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(DateTimeFormat datePlanted) {
        this.datePlanted = datePlanted;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

}