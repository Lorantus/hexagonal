package com.experiment.hexagonal.infrastructure.client.core.spi;

public interface ClientCreateUser {
    void setEmail(String email);
    void setPasswordHash(String passwordHash);
    void setGender(String gender);
    void setFullName(String fullName);
    boolean createUser();
}
