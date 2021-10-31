package com.experiment.hexagonal.infrastructure.client.adapter;

import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationAuthentification;
import com.experiment.hexagonal.infrastructure.application.core.model.AuthentificationPrincipal;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientAuthentification;
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
    public boolean isAuthentified(AuthentificationPrincipal authentificationPrincipal) {
        return applicationAuthentification.isAuthentified(authentificationPrincipal);
    }
}
