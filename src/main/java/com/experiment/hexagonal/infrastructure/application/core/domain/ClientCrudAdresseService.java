package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.core.api.model.AdresseDto;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationCrudAdresse;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientAdresse;
import com.experiment.hexagonal.infrastructure.application.core.spi.APICrudAdresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientCrudAdresseService implements ApplicationCrudAdresse {
    private final APICrudAdresse apiCrudAdresse;
    
    private UUID id;
    private String ville;

    @Autowired
    public ClientCrudAdresseService(APICrudAdresse apiCrudAdresse) {
        this.apiCrudAdresse = apiCrudAdresse;
    }   

    @Override
    public void setIdentifiant(UUID id) {
        this.id = id;
    }

    public String getVille() {
        return ville;
    }

    @Override
    public void setVille(String ville) {
        this.ville = ville;
    }
    
    @Override
    public boolean create() {
        AdresseDto adresse = new AdresseDto(ville);
        adresse.setVille(ville);
        return apiCrudAdresse.createAdresse(adresse).equals(Result.SUCCESS);
    } 

    @Override
    public boolean update() {
        AdresseDto adresse = new AdresseDto(IdentifiantDto.create(id), ville);
        return apiCrudAdresse.updateAdresse(adresse).equals(Result.SUCCESS);
    }

    @Override
    public boolean delete() {
        AdresseDto adresse = new AdresseDto(IdentifiantDto.create(id), ville);
        return apiCrudAdresse.deleteAdresse(adresse).equals(Result.SUCCESS);
    }
    
    private AdresseDto findByVille() {
        AdresseDto adresseDto = new AdresseDto(IdentifiantDto.create(id), ville);
        Result<AdresseDto> result = apiCrudAdresse.findByVille(adresseDto);
        if(result.is(ResultType.OK)) {
            return result.getData();
        }
        return null;
    }
    
    @Override
    public ClientAdresse executeFindVille() {       
        AdresseDto found = findByVille();
        if(null != found) {
            ClientAdresse clientAdresse = new ClientAdresse();            
            clientAdresse.setId(found.getIdentifiant().getId());
            clientAdresse.setVille(found.getVille());
            return clientAdresse;
        }
        return null;
    }
}
