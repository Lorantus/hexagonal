package com.experiment.hexagonal.infrastructure.application.core.domain;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.infrastructure.application.core.api.ApplicationDeleteUser;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIDeleteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientDeleteUserService implements ApplicationDeleteUser {
    private final APIDeleteUser apiDeleteUser;

    private UUID id;

    @Autowired
    public ClientDeleteUserService(APIDeleteUser apiDeleteUser) {
        this.apiDeleteUser = apiDeleteUser;
    }
    
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean execute() {
        return apiDeleteUser.deleteUser(IdentifiantDto.create(id)).getResultType().equals(ResultType.OK);
    }
}
