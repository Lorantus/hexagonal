package com.experiment.hexagonal.core.api.model;

import java.util.Objects;

public final class AuthentificationDto {
    private final LoginDto login;
    private final PasswordDto passwordHash;

    private AuthentificationDto(LoginDto login, PasswordDto passwordHash) {
        this.login = login;
        this.passwordHash = passwordHash;
    }

    public LoginDto getLogin() {
        return login;
    }

    public PasswordDto getPasswordHash() {
        return passwordHash;
    }

    public static AuthentificationDto create(LoginDto login, PasswordDto passwordHash) {
        return new AuthentificationDto(login, passwordHash);
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
