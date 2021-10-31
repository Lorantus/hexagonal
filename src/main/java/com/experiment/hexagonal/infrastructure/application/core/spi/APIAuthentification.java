package com.experiment.hexagonal.infrastructure.application.core.spi;

import com.experiment.hexagonal.infrastructure.application.core.model.AuthentificationPrincipal;

public interface APIAuthentification {
    boolean isAuthentified(AuthentificationPrincipal authentificationPrincipal);
}
