package com.experiment.hexagonal.infrastructure.application.core.api;

public interface ApplicationAuthentification {
    void setLogin(String login);
    void setPasswordHash(String passwordHash);
    boolean isAuthentified();
}
