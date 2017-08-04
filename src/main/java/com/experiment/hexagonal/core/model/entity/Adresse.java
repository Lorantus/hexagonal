package com.experiment.hexagonal.core.model.entity;

import com.experiment.hexagonal.core.model.valueobject.Identity;
import java.util.UUID;

public class Adresse extends Identity<UUID> {
    private String ville;

    private Adresse(UUID id) {
        super(id);
    }
    
    public static Adresse create(UUID uuid) {
        return new Adresse(uuid);
    }
    
    public static Adresse create(String uuid) {
        return new Adresse(UUID.fromString(uuid));
    }

    public static Adresse randomId() {
        return new Adresse(UUID.randomUUID());
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
