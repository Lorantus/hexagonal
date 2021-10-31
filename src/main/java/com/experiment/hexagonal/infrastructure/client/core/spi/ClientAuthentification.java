package com.experiment.hexagonal.infrastructure.client.core.spi;

import com.experiment.hexagonal.infrastructure.application.core.model.AuthentificationPrincipal;

public interface ClientAuthentification {
    boolean isAuthentified(AuthentificationPrincipal authentificationPrincipal);
}
