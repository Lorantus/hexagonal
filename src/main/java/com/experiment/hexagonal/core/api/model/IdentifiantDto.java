package com.experiment.hexagonal.core.api.model;

import java.util.UUID;

public class IdentifiantDto {
    private final UUID id;
    
    private IdentifiantDto(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
    
    public static IdentifiantDto create(UUID id) {
        return new IdentifiantDto(id);
    }
}
