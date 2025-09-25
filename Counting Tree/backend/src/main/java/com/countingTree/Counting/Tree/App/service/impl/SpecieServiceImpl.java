package com.countingTree.Counting.Tree.App.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.countingTree.Counting.Tree.App.model.Specie;
import com.countingTree.Counting.Tree.App.repository.SpecieRepository;
import com.countingTree.Counting.Tree.App.service.SpecieService;

import org.springframework.stereotype.Service;

@Service
public class SpecieServiceImpl implements SpecieService {

    @Autowired
    private SpecieRepository specieRepository;

    @Override
    public Specie getSpecieById(Long specieId) {
        Specie existingSpecie = specieRepository.findById(specieId)
                .orElseThrow(() -> new IllegalArgumentException("Specie with ID " + specieId + " not found."));
        return existingSpecie;
    }

    @Override
    public void addSpecie(Specie newSpecie) {
        validateSpecie(newSpecie);
        specieRepository.save(newSpecie);
    }

    @Override
    public void updateSpecie(Long specieId, Specie specie) {
        Specie existingSpecie = specieRepository.findById(specieId)
                .orElseThrow(() -> new IllegalArgumentException("Specie with ID " + specieId + " not found."));
        
        existingSpecie.setCommonName(specie.getCommonName());
        existingSpecie.setScientificName(specie.getScientificName());
        existingSpecie.setDescription(specie.getDescription());
        validateSpecie(specie);

        specieRepository.save(existingSpecie);
    }

    @Override
    public void deleteSpecie(Long specieId) {
        Specie existingSpecie = specieRepository.findById(specieId)
                .orElseThrow(() -> new IllegalArgumentException("Specie with ID " + specieId + " not found."));

        if (!existingSpecie.getPlants().isEmpty()) {
                throw new IllegalArgumentException("Specie with ID " + specieId + " has associated plants and cannot be deleted.");
        }else {
            specieRepository.deleteById(specieId);
        }
    }

    @Override
    public List<Specie> getAllSpecies() {
        return specieRepository.findAll();
    }
    
    // EXTRA METHODS

    private void validateSpecie(Specie specie) {
        if (specie == null) {
            throw new IllegalArgumentException("Specie must not be null");
        }
        if (specie.getCommonName() == null || specie.getCommonName().trim().isEmpty()) {
            throw new IllegalArgumentException("Common name must not be null or empty");
        }
        if (specie.getScientificName() == null || specie.getScientificName().trim().isEmpty()) {
            throw new IllegalArgumentException("Scientific name must not be null or empty");
        }
    }
}
