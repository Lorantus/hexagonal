package com.experiment.hexagonal.infrastructure.client.adapter;

import com.experiment.hexagonal.infrastructure.client.core.spi.ClientCreateUser;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientDeleteUser;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientUpdateUser;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationCreateUser;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationDeleteUser;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationUpdateUser;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientApplicationUserAdpateur implements ClientCreateUser, ClientUpdateUser, ClientDeleteUser {
    private final ApplicationCreateUser applicationCreateUser;
    private final ApplicationUpdateUser applicationUpdateUser;
    private final ApplicationDeleteUser applicationDeleteUser;

    @Autowired
    public ClientApplicationUserAdpateur(ApplicationCreateUser applicationCreateUser, ApplicationUpdateUser applicationUpdateUser, ApplicationDeleteUser applicationDeleteUser) {
        this.applicationCreateUser = applicationCreateUser;
        this.applicationUpdateUser = applicationUpdateUser;
        this.applicationDeleteUser = applicationDeleteUser;
    }

    @Override
    public void setId(UUID id) {
        applicationUpdateUser.setId(id);
        applicationDeleteUser.setId(id);
    }

    @Override
    public void setEmail(String email) {
        applicationCreateUser.setEmail(email);
        applicationUpdateUser.setEmail(email);
    }

    @Override
    public void setPasswordHash(String passwordHash) {
        applicationCreateUser.setPasswordHash(passwordHash);
    }

    @Override
    public void setGender(String gender) {
        applicationCreateUser.setGender(gender);
        applicationUpdateUser.setGender(gender);
    }

    @Override
    public void setFullName(String fullName) {
        applicationCreateUser.setFullName(fullName);
        applicationUpdateUser.setFullName(fullName);
    }

    @Override
    public boolean createUser() {
        return applicationCreateUser.execute();
    }

    @Override
    public boolean updateUser() {
        return applicationUpdateUser.execute();
    }

    @Override
    public void deleteUser() {
        applicationDeleteUser.execute();
    }
}
