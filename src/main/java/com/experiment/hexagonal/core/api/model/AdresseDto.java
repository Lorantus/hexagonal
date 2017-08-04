package com.experiment.hexagonal.core.api.model;

public class AdresseDto {
    private IdentifiantDto identifiantDto;
    private String ville;
        
    public IdentifiantDto getIdentifiant() {
        return identifiantDto;
    }

    public void setIdentifiant(IdentifiantDto id) {
        this.identifiantDto = id;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }
}
