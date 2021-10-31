package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.CreateUser;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserFactory;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService implements CreateUser {
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    @Autowired
    public CreateUserService(
            @Qualifier("inMemoryUserRepository")
                    UserRepository userRepository,
            UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public Result<?> createUser(UserCreateDto userCreate) {
        if (userCreate.getEmail().equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un email");
        }
        if (userCreate.getFullName().equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un nom");
        }

        return userRepository.findUserWithEmail(userCreate.getEmail())
                .map(found -> TransactionResult.asForbidden("Cet email existe déjà"))
                .orElseGet(() -> {
                    userRepository.put(
                            userFactory.buildUser(UserId.randomId(), userCreate.getEmail(), userCreate.getFullName())
                                    .withPasswordHash(Password.create(userCreate.getPasswordHash().getRawValue()))
                                    .withGender(Gender.valueOf(userCreate.getGender()))
                                    .build()
                    );
                    return TransactionResult.asSuccess();
                });
    }
}
