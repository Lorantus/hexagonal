package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.core.api.model.AdresseDto;
import com.experiment.hexagonal.core.api.transaction.Result;

public interface CrudAdresse {
    Result createAdresse(AdresseDto adresseDto);
    Result<?> updateAdresse(AdresseDto adresseDto);
    Result deleteAdresse(AdresseDto adresseDto);
    Result<AdresseDto> findAdresseByVille(AdresseDto adresseDto);
}