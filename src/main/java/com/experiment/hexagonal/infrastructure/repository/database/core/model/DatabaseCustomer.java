package com.experiment.hexagonal.infrastructure.repository.database.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DatabaseCustomer {       
    private final UUID uuid;
    private UUID userId;
    private UUID adresseId;

    public DatabaseCustomer(UUID uuid) {
        this.uuid = uuid;
    }
}
