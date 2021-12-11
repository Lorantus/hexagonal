package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.CreateUser;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserBuilder;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService implements CreateUser {
    private final UserRepository userRepository;

    @Autowired
    public CreateUserService(@Qualifier("inMemoryUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<?> createUser(UserCreateDto userCreate) {
        return userRepository.findUserWithEmail(userCreate.getEmail())
                .map(found -> TransactionResult.asForbidden("Cet email existe déjà"))
                .orElseGet(() -> {
                    Password passwordHash = Password.create(userCreate.getPasswordHash().getRawValue());
                    userRepository.put(
                            UserBuilder.buildUser(UserId.randomId(), userCreate.getEmail(), passwordHash, userCreate.getFullName())
                                    .withGender(Gender.valueOf(userCreate.getGender()))
                                    .build()
                    );
                    return TransactionResult.asSuccess();
                });
    }
}
