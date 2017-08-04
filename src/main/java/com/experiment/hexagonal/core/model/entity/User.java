package com.experiment.hexagonal.core.model.entity;

import com.experiment.hexagonal.core.model.valueobject.Gender;
import java.util.Objects;

public class User {    
    private final UserId id;
    private String email;
    private String passwordHash;
    private Gender gender;
    private String fullName;

    public User(UserId id) {
        this.id = id;
    }

    public UserId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.id, ((User) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
