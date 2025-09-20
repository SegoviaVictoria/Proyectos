package com.countingTree.Counting.Tree.App.model;

import org.springframework.format.annotation.DateTimeFormat;

public class PlantPhoto {
    
    private Long photoId;
    private String url;
    private DateTimeFormat dateTaken;

    private Plant plant;

    public PlantPhoto(Long photoId, String url, DateTimeFormat dateTaken, Plant plant) {
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

    public DateTimeFormat getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(DateTimeFormat dateTaken) {
        this.dateTaken = dateTaken;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    
}
