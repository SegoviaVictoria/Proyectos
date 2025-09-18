package com.countingTree.Counting.Tree.App.model;

public class Specie {
    
    private Long specieId;
    private String commonName;
    private String scientificName;
    private String description;

    public Specie(Long specieId, String commonName, String scientificName, String description) {
        this.specieId = specieId;
        this.commonName = commonName;
        this.scientificName = scientificName;
        this.description = description;
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

}
