package com.experiment.hexagonal.infrastructure.client.adapter;

import com.experiment.hexagonal.infrastructure.client.core.spi.ClientFindUserByEmail;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationFindUserByEmail;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientApplicationFindUserByEmailAdpateur implements ClientFindUserByEmail {
    private final ApplicationFindUserByEmail applicationFindUserByEmail;   

    @Autowired
    public ClientApplicationFindUserByEmailAdpateur(ApplicationFindUserByEmail applicationFindUserByEmail) {
        this.applicationFindUserByEmail = applicationFindUserByEmail;
    }

    @Override
    public void setEmail(String email) {
        applicationFindUserByEmail.setEmail(email);
    }

    @Override
    public ClientUser executeFind() {
        return applicationFindUserByEmail.executeFind();
    }

    @Override
    public String executeFindFullName() {
        return applicationFindUserByEmail.executeFindFullName();
    }
}
