package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.core.api.model.AuthentificationDto;

public interface Authentification {
    boolean isAuthentified(AuthentificationDto authentificationObject);
}
