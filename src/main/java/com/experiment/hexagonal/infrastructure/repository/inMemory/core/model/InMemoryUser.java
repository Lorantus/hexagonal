package com.experiment.hexagonal.infrastructure.repository.inMemory.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class InMemoryUser extends InMemoryEntity {       
    private String id;
    private String email;
    private String passwordHash;
    private String gender;
    private String fullName;

    public InMemoryUser(UUID uuid) {
        super(uuid);
    }
}
