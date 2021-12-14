package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationFindUserByEmail;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIFindByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientFindUserByEmailService implements ApplicationFindUserByEmail {
    private final APIFindByEmail apiFindByEmail;
    
    private String email;

    @Autowired
    public ClientFindUserByEmailService(APIFindByEmail apiFindByEmail) {
        this.apiFindByEmail = apiFindByEmail;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    
    private Result<UserUpdateDto> findByEmail() {
        return apiFindByEmail.findUserByEmail(email);
    }
    
    @Override
    public String executeFindFullName() {
        return findByEmail()
                .map(UserUpdateDto::getFullName)
                .orElse(null);
    }

    @Override
    public ClientUser executeFind() {
        return findByEmail()
                .map(found -> {
                    ClientUser clientUser = new ClientUser();
                    clientUser.setId(found.getIdentifiant().getId());
                    clientUser.setEmail(found.getEmail());
                    clientUser.setGender(found.getGender());
                    clientUser.setFullName(found.getFullName());
                    return clientUser;
                })
                .orElse(null);
    }
}
