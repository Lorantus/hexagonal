package com.experiment.hexagonal.core.model.entity;

import com.experiment.hexagonal.core.model.valueobject.Identity;
import java.util.UUID;

public final class UserId extends Identity<UUID> {
    private UserId(UUID id) {
        super(id);
    }
    
    public static UserId create(UUID uuid) {
        return new UserId(uuid);
    }
    
    public static UserId create(String uuid) {
        return new UserId(UUID.fromString(uuid));
    }

    public static UserId randomId() {
        return new UserId(UUID.randomUUID());
    }
}
