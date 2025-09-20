package com.countingTree.Counting.Tree.App.model;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "alerts")
public class Alert {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;

    @Column(name = "type", nullable = false)
    private String type;
    
    @Column(name = "message")
    private String message;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    
    // -------------------------------------------------------- RELATIONS

    @Enumerated(EnumType.STRING)
    private AlertStatus status = AlertStatus.PENDING;

    @ManyToMany
    @JoinTable(
        name = "plant_alert",
        joinColumns = @JoinColumn(name = "alert_id"),
        inverseJoinColumns = @JoinColumn(name = "plant_id")
    )
    @JsonManagedReference
    private Set<Plant> plants = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    @JsonBackReference
    private User creator;

    @ManyToOne
    @JoinColumn(name = "resolved_by")
    @JsonBackReference
    private User resolver;

    
    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public Alert(Long alertId, String type, String message, LocalDateTime creationDate, AlertStatus status, Set<Plant> plants, User creator, User resolver) {
        this.alertId = alertId;
        this.type = type;
        this.message = message;
        this.creationDate = creationDate;
        this.status = status;
        this.plants = plants;
        this.creator = creator;
        this.resolver = resolver;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public AlertStatus getStatus() {
        return status;
    }

    public void setStatus(AlertStatus status) {
        this.status = status;
    }

    public Set<Plant> getPlants() {
        return plants;
    }

    public void setPlants(Set<Plant> plants) {
        this.plants = plants;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getResolver() {
        return resolver;
    }

    public void setResolver(User resolver) {
        this.resolver = resolver;
    }

}
