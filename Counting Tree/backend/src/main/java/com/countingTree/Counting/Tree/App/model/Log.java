package com.countingTree.Counting.Tree.App.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    @Column(name = "action_date", nullable = false)
    private LocalDateTime actionDate;

    // -------------------------------------------------------- RELATIONS

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User performedBy;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant relatedPlant;

    @ManyToOne
    @JoinColumn(name = "alert_id")
    private Alert relatedAlert;
    
    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public Log(Long logId, String actionType, LocalDateTime actionDate, User performedBy, Plant relatedPlant, Alert relatedAlert) {
        this.logId = logId;
        this.actionType = actionType;
        this.actionDate = actionDate;
        this.performedBy = performedBy;
        this.relatedPlant = relatedPlant;
        this.relatedAlert = relatedAlert;
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

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public User getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(User performedBy) {
        this.performedBy = performedBy;
    }

    public Plant getRelatedPlant() {
        return relatedPlant;
    }

    public void setRelatedPlant(Plant relatedPlant) {
        this.relatedPlant = relatedPlant;
    }

    public Alert getRelatedAlert() {
        return relatedAlert;
    }

    public void setRelatedAlert(Alert relatedAlert) {
        this.relatedAlert = relatedAlert;
    }

}
