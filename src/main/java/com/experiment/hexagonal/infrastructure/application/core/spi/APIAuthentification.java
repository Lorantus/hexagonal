package com.experiment.hexagonal.infrastructure.application.core.spi;

public interface APIAuthentification {
    boolean isAuthentified(String login, String passwordHash);
}
