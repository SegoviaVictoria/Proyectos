package com.countingTree.Counting.Tree.App.service;

import java.util.List;
import com.countingTree.Counting.Tree.App.model.Specie;

public interface SpecieService {

    Specie getSpecieById(Long specieId);

    void addSpecie(Specie newSpecie);

    void updateSpecie(Long specieId, Specie specie);

    void deleteSpecie(Long specieId);

    List<Specie> getAllSpecies();

}
