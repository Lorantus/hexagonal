package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIUpdateUser;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationUpdateUser;

@Service
public class ClientUpdateUserService implements ApplicationUpdateUser {
    private final APIUpdateUser apiUpdateUser;
    
    private UUID id;
    private String email;
    private String gender;
    private String fullName;

    @Autowired
    public ClientUpdateUserService(APIUpdateUser apiUpdateUser) {
        this.apiUpdateUser = apiUpdateUser;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
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
        UserUpdateDto user = new UserUpdateDto();
        IdentifiantDto userIdDto = IdentifiantDto.create(id);
        user.setIdentifiant(userIdDto);
        user.setEmail(email);
        user.setGender(gender);
        user.setFullName(fullName);
        return apiUpdateUser.updateUser(user).getResultType().equals(ResultType.OK);
    }
}
