package com.countingTree.Counting.Tree.App.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plantId;

    @Column(name = "main_photo")
    private String mainPhoto;

    @Column(name = "date_planted")
    private LocalDateTime datePlanted;

    
    // -------------------------------------------------------- RELATIONS

    @Embedded
    private Coordinate location;

    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    private Specie species;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToOne
    @JoinColumn(name = "verification_status_id", nullable = false)
    private VerificationStatus verificationStatus;

    @ManyToOne
    @JoinColumn(name = "health_status_id")
    @JsonBackReference
    private HealthStatus healthStatus;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PlantPhoto> photos = new HashSet<>();

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany(mappedBy = "alert")
    @JsonBackReference
    private Set<Alert> alerts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;


    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public Plant(Long plantId, String mainPhoto, Coordinate location, LocalDateTime datePlanted,
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

    public LocalDateTime getDatePlanted() {
        return datePlanted;
    }

    public void setDatePlanted(LocalDateTime datePlanted) {
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
