package com.experiment.hexagonal.core.api.model;

public class PasswordDto {
    private final String rawValue;

    public PasswordDto(String rawValue) {
        if (rawValue.equals("")) {
            throw new IllegalArgumentException("Le hash est vide");
        }
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return rawValue;
    }
}
