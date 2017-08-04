package com.experiment.hexagonal.infrastructure.client.adapter;

import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationCrudAdresse;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientAdresse;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientCrudAdresse;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientApplicationCrudAdresseAdpateur implements ClientCrudAdresse {
    private final ApplicationCrudAdresse applicationCrudAdresse;

    @Autowired
    public ClientApplicationCrudAdresseAdpateur(ApplicationCrudAdresse applicationCrudAdresse) {
        this.applicationCrudAdresse = applicationCrudAdresse;
    }

    @Override
    public void setIdentifiant(UUID id) {
        applicationCrudAdresse.setIdentifiant(id);
    }

    @Override
    public void setVille(String ville) {
        applicationCrudAdresse.setVille(ville);
    }

    @Override
    public boolean createAdresse() {
        return applicationCrudAdresse.create();
    }

    @Override
    public boolean updateAdresse() {
        return applicationCrudAdresse.update();
    }

    @Override
    public void deleteAdresse() {
        applicationCrudAdresse.delete();
    }

    @Override
    public ClientAdresse executeFind() {
        return applicationCrudAdresse.executeFindVille();
    }
}
