package com.experiment.hexagonal.core.api.model;

public class PasswordDto {
    private final String rawValue;

    public PasswordDto(String rawValue) {
        this.rawValue = rawValue;
    }

    public String getRawValue() {
        return rawValue;
    }
}
