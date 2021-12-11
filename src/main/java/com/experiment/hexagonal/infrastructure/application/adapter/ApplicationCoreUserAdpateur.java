package com.experiment.hexagonal.infrastructure.application.adapter;

import com.experiment.hexagonal.core.api.CreateUser;
import com.experiment.hexagonal.core.api.DeleteUser;
import com.experiment.hexagonal.core.api.UpdateUser;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.infrastructure.application.core.spi.APICreateUser;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIDeleteUser;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIUpdateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCoreUserAdpateur implements APICreateUser, APIUpdateUser, APIDeleteUser {
    private final CreateUser createUser;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;

    @Autowired
    public ApplicationCoreUserAdpateur(CreateUser createUser, UpdateUser updateUser, DeleteUser deleteUser) {
        this.createUser = createUser;
        this.updateUser = updateUser;
        this.deleteUser = deleteUser;
    }
    
    @Override
    public Result<?> createUser(UserCreateDto user) {
        return createUser.createUser(user);
    }

    @Override
    public Result<?> updateUser(UserUpdateDto user) {
        return updateUser.updateUser(user);
    }

    @Override
    public Result<?> deleteUser(IdentifiantDto identifiantDto) {
        return deleteUser.deleteUser(identifiantDto);
    }
}
