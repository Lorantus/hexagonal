package com.experiment.hexagonal.core.api.model;

public class LoginDto {
    private final String loginValue;

    public LoginDto(String loginValue) {
        this.loginValue = loginValue;
    }

    public String getLoginValue() {
        return loginValue;
    }
}
