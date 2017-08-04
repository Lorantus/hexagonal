package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIFindByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationFindUserByEmail;

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
    
    private UserUpdateDto findByEmail() {
        UserUpdateDto user = new UserUpdateDto();
        user.setEmail(email);
        Result<UserUpdateDto> result = apiFindByEmail.findUserByEmail(user);
        if(result.is(ResultType.OK)) {
            return result.getData();
        }
        return null;
    }
    
    @Override
    public String executeFindFullName() {
        UserUpdateDto found = findByEmail();        
        return null == found  ? null : found.getFullName();
    }

    @Override
    public ClientUser executeFind() {        
        UserUpdateDto found = findByEmail();
        if(null != found) {
            ClientUser clientUser = new ClientUser();            
            clientUser.setId(found.getIdentifiant().getId());
            clientUser.setEmail(found.getEmail());
            clientUser.setGender(found.getGender());
            clientUser.setFullName(found.getFullName());
            return clientUser;
        }
        return null;
    }
}
