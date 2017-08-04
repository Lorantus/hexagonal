package com.experiment.hexagonal.infrastructure.application.core.api;

public interface ApplicationCreateUser {
    void setEmail(String email);
    void setPasswordHash(String passwordHash);
    void setGender(String gender);
    void setFullName(String fullName);
    boolean execute();
}
