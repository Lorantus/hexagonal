package com.experiment.hexagonal.infrastructure.application.adapter;

import com.experiment.hexagonal.core.api.FindUser;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIFindByEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApplicationCoreFindUserByEmailAdpateur implements APIFindByEmail {
    private final FindUser findUser;

    @Autowired
    public ApplicationCoreFindUserByEmailAdpateur(FindUser findUser) {
        this.findUser = findUser;
    }

    @Override
    public Result<UserUpdateDto> findUserByEmail(String email) {
        return findUser.findUserByEmail(email);
    }
}
