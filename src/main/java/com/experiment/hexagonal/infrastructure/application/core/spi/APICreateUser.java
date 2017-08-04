package com.experiment.hexagonal.infrastructure.application.core.spi;

import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.transaction.Result;

public interface APICreateUser {
    Result createUser(UserCreateDto userCreate);
}