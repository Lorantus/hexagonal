package com.experiment.hexagonal.infrastructure.repository.inMemory.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InMemoryCustomer extends InMemoryEntity {       
    private UUID userId;
    private UUID adresseId;

    public InMemoryCustomer(UUID uuid) {
        super(uuid);
    }
    
}
