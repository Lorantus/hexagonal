package com.experiment.hexagonal.core.api.model;

public class UserUpdateDto {
    private final IdentifiantDto identifiantDto;
    private final String email;
    private final String fullName;
    private String gender;

    public UserUpdateDto(IdentifiantDto identifiantDto, String email, String fullName) {
        if (email.equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un email");
        }
        if (fullName.equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un nom");
        }
        this.identifiantDto = identifiantDto;
        this.email = email;
        this.fullName = fullName;
    }

    public IdentifiantDto getIdentifiant() {
        return identifiantDto;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
