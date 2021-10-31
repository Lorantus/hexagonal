package com.experiment.hexagonal.infrastructure.repository.inMemory.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InMemoryAdresse extends InMemoryEntity {       
    private UUID id;
    private String ville;

    public InMemoryAdresse(UUID uuid) {
        super(uuid);
    }
}
