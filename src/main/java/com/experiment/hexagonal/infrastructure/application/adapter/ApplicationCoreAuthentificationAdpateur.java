package com.experiment.hexagonal.infrastructure.application.adapter;

import com.experiment.hexagonal.core.api.Authentification;
import com.experiment.hexagonal.core.api.model.AuthentificationDto;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIAuthentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCoreAuthentificationAdpateur implements APIAuthentification {
    private final Authentification authentification;

    @Autowired
    public ApplicationCoreAuthentificationAdpateur(Authentification authentification) {
        this.authentification = authentification;
    }

    @Override
    public boolean isAuthentified(String login, String passwordHash) {
        AuthentificationDto authentificationObject = AuthentificationDto.create(login, passwordHash);
        return authentification.isAuthentified(authentificationObject);
    }
}
