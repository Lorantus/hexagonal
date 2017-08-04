package com.experiment.hexagonal.infrastructure.repository.database.core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class DatabaseAdresse {       
    private final UUID uuid;
    private String id;
    private String ville;

    public DatabaseAdresse(UUID uuid) {
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
        return Objects.equals(this.uuid, ((DatabaseAdresse) obj).uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
