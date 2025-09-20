package com.countingTree.Counting.Tree.App.model;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "species")
public class Specie {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specieId;

    @Column(name = "common_name", nullable = false)
    private String commonName;

    @Column(name = "scientific_name", nullable = false, unique = true)
    private String scientificName;

    @Column(name = "description")
    private String description;

    // -------------------------------------------------------- RELATIONS

    @OneToMany(mappedBy = "species")
    @JsonManagedReference
    private Set<Plant> plants = new HashSet<>();

    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public Specie(Long specieId, String commonName, String scientificName, String description, Set<Plant> plants) {
        this.specieId = specieId;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.description = description;
        this.plants = plants;
    }

    public Specie() {
    }

    public Long getSpecieId() {
        return specieId;
    }

    public void setSpecieId(Long specieId) {
        this.specieId = specieId;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
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
