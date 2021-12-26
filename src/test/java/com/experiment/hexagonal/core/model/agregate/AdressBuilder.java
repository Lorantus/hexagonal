package com.experiment.hexagonal.core.model.agregate;

import com.experiment.hexagonal.core.model.entity.Adresse;

import java.util.UUID;

public class AdressBuilder {
    private UUID id;
    private String ville;

    private AdressBuilder() {
        // Constructeur privé
    }

    public static AdressBuilder createAdressBuilder() {
        return new AdressBuilder()
                .withId(UUID.randomUUID());
    }

    public AdressBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public AdressBuilder withVille(String ville) {
        this.ville = ville;
        return this;
    }

    public Adresse build() {
        return Adresse.create(id, ville);
    }
}
