package com.experiment.hexagonal.core.api.model;

public class AdresseDto {
    private final IdentifiantDto identifiantDto;
    private final String ville;

    public AdresseDto(IdentifiantDto identifiantDto, String ville) {
        if (ville.equals("")) {
            throw new IllegalArgumentException("La adresse doit avoir une ville");
        }

        this.identifiantDto = identifiantDto;
        this.ville = ville;
    }

    public AdresseDto(String ville) {
        this(null, ville);
    }

    public IdentifiantDto getIdentifiant() {
        return identifiantDto;
    }

    public String getVille() {
        return ville;
    }

    public AdresseDto setVille(String ville) {
        return new AdresseDto(this.identifiantDto, ville);
    }
}
