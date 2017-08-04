package com.experiment.hexagonal.infrastructure.client.adapter;

import com.experiment.hexagonal.infrastructure.client.core.spi.ClientAuthentification;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationAuthentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientApplicationAuthentificationAdpateur implements ClientAuthentification {
    private final ApplicationAuthentification applicationAuthentification;

    @Autowired
    public ClientApplicationAuthentificationAdpateur(ApplicationAuthentification applicationAuthentification) {
        this.applicationAuthentification = applicationAuthentification;
    }

    @Override
    public void setLogin(String login) {
        applicationAuthentification.setLogin(login);
    }

    @Override
    public void setPasswordHash(String passwordHash) {
        applicationAuthentification.setPasswordHash(passwordHash);
    }

    @Override
    public boolean isAuthentified() {
        return applicationAuthentification.isAuthentified();
    }
}
