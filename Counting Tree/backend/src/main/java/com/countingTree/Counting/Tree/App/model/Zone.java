package com.countingTree.Counting.Tree.App.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "zones")
public class Zone {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long zoneId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    // -------------------------------------------------------- RELATIONS

    @ElementCollection
    private List<Coordinate> coordinates;

    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public Zone(Long zoneId, String name, String description, List<Coordinate> coordinates) {
        this.zoneId = zoneId;
        this.name = name;
        this.description = description;
        this.coordinates = coordinates;
    }

    public Zone() {
    }

    public Long getZoneId() {
        return zoneId;
    }

    public void setZoneId(Long zoneId) {
        this.zoneId = zoneId;
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

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

}
