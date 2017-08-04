package com.experiment.hexagonal.infrastructure.application.core.api;

import com.experiment.hexagonal.infrastructure.application.core.model.ClientAdresse;
import java.util.UUID;

public interface ApplicationCrudAdresse {
    void setIdentifiant(UUID id);
    void setVille(String email);
    boolean create();
    boolean update();
    boolean delete();
    
    ClientAdresse executeFindVille();
}
