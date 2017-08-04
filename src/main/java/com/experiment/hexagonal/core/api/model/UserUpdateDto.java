package com.experiment.hexagonal.core.api.model;

public class UserUpdateDto {
    private IdentifiantDto identifiantDto;
    private String email;
    private String gender;
    private String fullName;
    
    public IdentifiantDto getIdentifiant() {
        return identifiantDto;
    }

    public void setIdentifiant(IdentifiantDto id) {
        this.identifiantDto = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
