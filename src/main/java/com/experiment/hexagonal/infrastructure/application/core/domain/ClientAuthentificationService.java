package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.infrastructure.application.core.spi.APIAuthentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationAuthentification;

@Service
public class ClientAuthentificationService implements ApplicationAuthentification {
    private final APIAuthentification apiAuthentification;
    private String login;
    private String passwordHash;

    @Autowired
    public ClientAuthentificationService(APIAuthentification apiAuthentification) {
        this.apiAuthentification = apiAuthentification;
    }
    
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    @Override
    public boolean isAuthentified() {
        return apiAuthentification.isAuthentified(login, passwordHash);
    }
}
