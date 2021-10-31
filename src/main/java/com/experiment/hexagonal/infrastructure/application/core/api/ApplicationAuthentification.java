package com.experiment.hexagonal.infrastructure.application.core.api;

import com.experiment.hexagonal.infrastructure.application.core.model.AuthentificationPrincipal;

public interface ApplicationAuthentification {
    boolean isAuthentified(AuthentificationPrincipal authentificationPrincipal);
}
