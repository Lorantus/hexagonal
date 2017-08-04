package com.experiment.hexagonal.infrastructure.client.core.spi;

import java.util.UUID;

public interface ClientDeleteUser {
    void setId(UUID id);
    void deleteUser();
}
