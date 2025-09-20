package com.countingTree.Counting.Tree.App.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "health_statuses")
public class HealthStatus {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    // -------------------------------------------------------- RELATIONS

    @OneToMany(mappedBy = "healthStatus", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Plant> plants = new HashSet<>();
    
    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public HealthStatus(Long statusId, String name, String description, Set<Plant> plants) {
        this.statusId = statusId;
        this.name = name;
        this.description = description;
        this.plants = plants;
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

    public Set<Plant> getPlants() {
        return plants;
    }

    public void setPlants(Set<Plant> plants) {
        this.plants = plants;
    }
    
}
