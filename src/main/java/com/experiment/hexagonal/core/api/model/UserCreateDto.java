package com.experiment.hexagonal.core.api.model;

public class UserCreateDto { 
    private String email;
    private PasswordDto passwordHash;
    private String gender;
    private String fullName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PasswordDto getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(PasswordDto passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
