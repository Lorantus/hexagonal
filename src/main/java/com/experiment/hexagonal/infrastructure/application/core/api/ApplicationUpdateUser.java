package com.experiment.hexagonal.infrastructure.application.core.api;

import java.util.UUID;

public interface ApplicationUpdateUser {
    void setId(UUID id);  
    void setEmail(String email);
    void setGender(String gender);
    void setFullName(String fullName);
    boolean execute();
}
