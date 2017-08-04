package com.experiment.hexagonal.core.api.model;

import java.util.Objects;

public final class AuthentificationDto {
    private final String login;
    private final String passwordHash;

    private AuthentificationDto(String login, String passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public String getLogin() {
        return login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    
    public static AuthentificationDto create(String email, String passwordHash) {
        return new AuthentificationDto(email, passwordHash);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.login);
        hash = 61 * hash + Objects.hashCode(this.passwordHash);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AuthentificationDto other = (AuthentificationDto) obj;
        if (!Objects.equals(this.login, other.login)) {
            return false;
        }
        return Objects.equals(this.passwordHash, other.passwordHash);
    }
    
    
}
