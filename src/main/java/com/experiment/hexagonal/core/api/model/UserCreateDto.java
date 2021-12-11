package com.experiment.hexagonal.core.api.model;

public class UserCreateDto {
    private final String email;
    private final PasswordDto passwordHash;
    private String gender;
    private String fullName;

    public UserCreateDto(String email, PasswordDto password) {
        if (email.equals("")) {
            throw new IllegalArgumentException("L'email est vide");
        }
        this.email = email;
        this.passwordHash = password;
    }

    public String getEmail() {
        return email;
    }

    public PasswordDto getPasswordHash() {
        return passwordHash;
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
