package com.experiment.hexagonal.core.model.entity;

import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;

import java.util.Objects;

public class User {
    private final UserId id;
    private final String email;
    private final Password passwordHash;
    private final String fullName;
    private Gender gender;

    public User(UserId id, String email, Password password, String fullName) {
        if (email.equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un email");
        }
        if (fullName.equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un nom");
        }
        this.id = id;
        this.email = email;
        this.passwordHash = password;
        this.fullName = fullName;
    }

    public UserId getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Password getPasswordHash() {
        return passwordHash;
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
