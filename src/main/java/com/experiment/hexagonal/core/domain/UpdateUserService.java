package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.UpdateUser;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserBuilder;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService implements UpdateUser {
    private final UserRepository userRepository;

    @Autowired
    public UpdateUserService(@Qualifier("inMemoryUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<?> updateUser(UserUpdateDto userUpdate) {
        return userRepository.findUserWithEmail(userUpdate.getEmail())
                .filter(user -> !user.getId().getIdentity().equals(userUpdate.getIdentifiant().getId()))
                .map(found -> TransactionResult.asForbidden("Cet email existe déjà"))
                .orElseGet(() -> {
                    IdentifiantDto userIdDto = userUpdate.getIdentifiant();
                    return userRepository.get(userIdDto.getId())
                            .map(user -> {
                                userRepository.put(
                                        UserBuilder.buildUser(user.getId(), userUpdate.getEmail(), user.getPasswordHash(), userUpdate.getFullName())
                                                .withGender(Gender.valueOf(userUpdate.getGender()))
                                                .build()
                                );
                                return TransactionResult.asSuccess();
                            })
                            .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
                });
    }
}
