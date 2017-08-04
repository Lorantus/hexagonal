package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.transaction.Result;

public interface CreateUser {
    Result<UserCreateDto> createUser(UserCreateDto userCreate);
}