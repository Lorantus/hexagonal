package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationAuthentification;
import com.experiment.hexagonal.infrastructure.application.core.model.AuthentificationPrincipal;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIAuthentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAuthentificationService implements ApplicationAuthentification {
    private final APIAuthentification apiAuthentification;

    @Autowired
    public ClientAuthentificationService(APIAuthentification apiAuthentification) {
        this.apiAuthentification = apiAuthentification;
    }

    @Override
    public boolean isAuthentified(AuthentificationPrincipal authentificationPrincipal) {
        return apiAuthentification.isAuthentified(authentificationPrincipal);
    }
}
