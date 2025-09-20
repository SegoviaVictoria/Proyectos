package com.countingTree.Counting.Tree.App.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

public class Plant {

    private Long plantId;
    private String mainPhoto;
    private Coordinate location;
    private DateTimeFormat datePlanted;

    private VerificationStatus verificationStatus;
    private HealthStatus healthStatus;

    private Specie species;
    private User owner;
    private Set<PlantPhoto> photos = new HashSet<>();
    private Set<Comment> comments = new HashSet<>();
    private Set<Alert> alerts = new HashSet<>();
    private Zone zone; // Es necesaria la Zonas?

    public Plant(Long plantId, String mainPhoto, Coordinate location, DateTimeFormat datePlanted,
            VerificationStatus verificationStatus, HealthStatus healthStatus, Specie species, User owner, Zone zone) {
        this.plantId = plantId;
        this.mainPhoto = mainPhoto;
        this.location = location;
        this.datePlanted = datePlanted;
        this.verificationStatus = verificationStatus;
        this.healthStatus = healthStatus;
        this.species = species;
        this.owner = owner;
        this.zone = zone;
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
        this.location = location;
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

    public Specie getSpecies() {
        return species;
    }

    public void setSpecies(Specie species) {
        this.species = species;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<PlantPhoto> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<PlantPhoto> photos) {
        this.photos = photos;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(Set<Alert> alerts) {
        this.alerts = alerts;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    
}