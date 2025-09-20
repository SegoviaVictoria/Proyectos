package com.countingTree.Counting.Tree.App.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.*;

@Entity
@Table(name = "plant_photos")
public class PlantPhoto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long photoId;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "date_taken", nullable = false)
    private LocalDateTime dateTaken;


    // -------------------------------------------------------- RELATIONS

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_id", nullable = false)
    @JsonBackReference
    private Plant plant;

    // -------------------------------------------------------- CONSTRUCTORS, GETTERS AND SETTERS

    public PlantPhoto(Long photoId, String url, LocalDateTime dateTaken, Plant plant) {
        this.photoId = photoId;
        this.url = url;
        this.dateTaken = dateTaken;
        this.plant = plant;
    }

    public PlantPhoto() {
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(LocalDateTime dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    
}
