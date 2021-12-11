package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.transaction.Result;

public interface DeleteUser {
    Result<?> deleteUser(IdentifiantDto identifiantDto);
}
