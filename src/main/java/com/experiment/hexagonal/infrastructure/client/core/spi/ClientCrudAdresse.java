package com.experiment.hexagonal.infrastructure.client.core.spi;

import com.experiment.hexagonal.infrastructure.application.core.model.ClientAdresse;
import java.util.UUID;

public interface ClientCrudAdresse {
    void setIdentifiant(UUID id);
    void setVille(String ville);
    boolean createAdresse();
    boolean updateAdresse();
    void deleteAdresse();
    
    ClientAdresse executeFind();
}
