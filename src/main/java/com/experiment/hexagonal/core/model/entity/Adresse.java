package com.experiment.hexagonal.core.model.entity;

import com.experiment.hexagonal.core.model.valueobject.Identity;

import java.util.UUID;

public class Adresse extends Identity<UUID> {
    private final String ville;

    private Adresse(UUID id, String ville) {
        super(id);
        this.ville = ville;
    }

    public static Adresse create(UUID uuid, String ville) {
        return new Adresse(uuid, ville);
    }

    public String getVille() {
        return ville;
    }

    public Adresse setVille(String ville) {
        return create(getIdentity(), ville);
    }
}
