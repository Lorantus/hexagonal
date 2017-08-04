package com.experiment.hexagonal.infrastructure.application.core.model;

import java.util.UUID;

public class ClientAdresse {    
    private UUID id;
    private String ville;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
