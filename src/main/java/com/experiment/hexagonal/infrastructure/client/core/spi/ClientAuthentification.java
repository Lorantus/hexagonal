package com.experiment.hexagonal.infrastructure.client.core.spi;

public interface ClientAuthentification {
    void setLogin(String login);
    void setPasswordHash(String passwordHash);
    boolean isAuthentified();
}
