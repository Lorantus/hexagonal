package com.experiment.hexagonal.infrastructure.application.core.api;

import java.util.UUID;

public interface ApplicationDeleteUser {
    void setId(UUID id);
    boolean execute();
}
