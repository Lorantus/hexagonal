package com.experiment.hexagonal.infrastructure.client.core.spi;

import java.util.UUID;

public interface ClientUpdateUser {
    void setId(UUID id);  
    void setEmail(String email);
    void setFullName(String fullName);
    void setGender(String gender);
    boolean updateUser();
}
