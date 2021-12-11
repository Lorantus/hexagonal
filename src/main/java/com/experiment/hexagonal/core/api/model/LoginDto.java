package com.experiment.hexagonal.core.api.model;

public class LoginDto {
    private final String loginValue;

    public LoginDto(String loginValue) {
        if (loginValue.equals("")) {
            throw new IllegalArgumentException("Le login est vide");
        }
        this.loginValue = loginValue;
    }

    public String getLoginValue() {
        return loginValue;
    }
}
