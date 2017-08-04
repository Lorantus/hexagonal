package com.experiment.hexagonal.infrastructure.application.core.spi;

import com.experiment.hexagonal.core.api.model.AdresseDto;
import com.experiment.hexagonal.core.api.transaction.Result;

public interface APICrudAdresse {
    Result createAdresse(AdresseDto adresseDto);
    Result updateAdresse(AdresseDto adresseDto);
    Result deleteAdresse(AdresseDto adresseDto);

    Result<AdresseDto> findByVille(AdresseDto adresseDto);
}