package com.experiment.hexagonal.infrastructure.repository.database.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class DatabaseUser {       
    private final UUID uuid;
    private String id;
    private String email;
    private String passwordHash;
    private String gender;
    private String fullName;

    public DatabaseUser(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.uuid, ((DatabaseUser) obj).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
