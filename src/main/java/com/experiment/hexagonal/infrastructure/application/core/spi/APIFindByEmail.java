package com.experiment.hexagonal.infrastructure.application.core.spi;

import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;

public interface APIFindByEmail {
    Result<UserUpdateDto> findUserByEmail(UserUpdateDto user);
}
