package com.experiment.hexagonal.infrastructure.application.adapter;

import com.experiment.hexagonal.core.api.CrudAdresse;
import com.experiment.hexagonal.core.api.model.AdresseDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.infrastructure.application.core.spi.APICrudAdresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCoreAdresseAdpateur implements APICrudAdresse {
    private final CrudAdresse crudAdresse;

    @Autowired
    public ApplicationCoreAdresseAdpateur(CrudAdresse crudAdresse) {
        this.crudAdresse = crudAdresse;
    }
    
    @Override
    public Result createAdresse(AdresseDto adresseDto) {
        return crudAdresse.createAdresse(adresseDto);
    }

    @Override
    public Result updateAdresse(AdresseDto adresseDto) {
        return crudAdresse.updateAdresse(adresseDto);
    }

    @Override
    public Result deleteAdresse(AdresseDto adresseDto) {
        return crudAdresse.deleteAdresse(adresseDto);
    }

    @Override
    public Result<AdresseDto> findByVille(AdresseDto adresseDto) {
        return crudAdresse.findAdresseByVille(adresseDto);
    }
}
