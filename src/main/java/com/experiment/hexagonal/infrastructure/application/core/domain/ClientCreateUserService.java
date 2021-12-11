package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.core.api.model.PasswordDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationCreateUser;
import com.experiment.hexagonal.infrastructure.application.core.spi.APICreateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientCreateUserService implements ApplicationCreateUser {
    private final APICreateUser apiCreateUser;
    
    private String email;
    private String passwordHash;
    private String gender;
    private String fullName;

    @Autowired
    public ClientCreateUserService(APICreateUser apiCreateUser) {
        this.apiCreateUser = apiCreateUser;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getFullName() {
        return fullName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    @Override
    public boolean execute() {
        UserCreateDto user = new UserCreateDto(email, new PasswordDto(passwordHash));
        user.setGender(gender);
        user.setFullName(fullName);
        return apiCreateUser.createUser(user).equals(Result.SUCCESS);
    }    
}
