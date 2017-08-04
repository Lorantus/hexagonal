package com.experiment.hexagonal.core.spi;

import com.experiment.hexagonal.core.model.entity.Adresse;

import java.util.Optional;
import java.util.UUID;

public interface AdresseRepository {
    Optional<Adresse> get(UUID id);
    void put(Adresse adresse);
    void remove(Adresse adresse);
    Optional<Adresse> findAdresseWithVille(String ville);
    void clear();
}