package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.CrudAdresse;
import com.experiment.hexagonal.core.api.model.AdresseDto;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.spi.AdresseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AdresseService implements CrudAdresse {
    private final AdresseRepository adresseRepository;
    
    @Autowired
    public AdresseService(
            @Qualifier("inMemoryAdresseRepository") AdresseRepository adresseRepository) {
        this.adresseRepository = adresseRepository;
    }
    
    @Override
    public Result createAdresse(AdresseDto adresseDto) {
        return adresseRepository.findAdresseWithVille(adresseDto.getVille())
                .map(found -> TransactionResult.asForbidden("Cette ville existe déjà"))
                .orElseGet(()-> {
                    Adresse adresse = Adresse.create(UUID.randomUUID());
                    adresse.setVille(adresseDto.getVille());
                    adresseRepository.put(adresse);
                    return TransactionResult.asSuccess();
                });
    }
    
    @Override
    public Result updateAdresse(AdresseDto adresseDto) {
        IdentifiantDto identifiantDto = adresseDto.getIdentifiant();  
        return adresseRepository.get(identifiantDto.getId())
                .map(adresse -> {
                    adresse.setVille(adresseDto.getVille());
                    adresseRepository.put(adresse);
                    return TransactionResult.asSuccess();
                })
                .orElse(TransactionResult.asBadRequest("L'adresse n'existe pas"));
    }
    
    @Override
    public Result deleteAdresse(AdresseDto adresseDto) {
        IdentifiantDto identifiantDto = adresseDto.getIdentifiant();
        return adresseRepository.get(identifiantDto.getId())
                .map(adresse -> {
                    adresseRepository.remove(adresse);
                    return TransactionResult.asSuccess();
                })
                .orElse(TransactionResult.asBadRequest("L'adresse n'existe pas"));
    }

    @Override
    public Result<AdresseDto> findAdresseByVille(AdresseDto adresseDto) {
        return adresseRepository.findAdresseWithVille(adresseDto.getVille())
                .map(adresse -> {
                    AdresseDto adresseResult = new AdresseDto();
                    IdentifiantDto identifiantDto = IdentifiantDto.create(adresse.getIdentity());
                    adresseResult.setIdentifiant(identifiantDto);
                    adresseResult.setVille(adresseDto.getVille());
                    return TransactionResult.asSuccess(adresseResult);
                })
                .orElse(TransactionResult.asBadRequest("L'adresse n'existe pas"));
    }
}
