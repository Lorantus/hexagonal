package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.DeleteUser;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserService implements DeleteUser {
    private final UserRepository userRepository;

    @Autowired
    public DeleteUserService(@Qualifier("inMemoryUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<?> deleteUser(UserUpdateDto userUpdate) {
        IdentifiantDto userIdDto = userUpdate.getIdentifiant();
        return userRepository.get(userIdDto.getId())
                .map(user -> {
                    userRepository.remove(user);
                    return TransactionResult.asSuccess();
                })
                .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
    }
}
