package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.UpdateUser;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserFactory;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService implements UpdateUser {
    private final UserFactory userFactory;
    private final UserRepository userRepository;

    @Autowired
    public UpdateUserService(
            @Qualifier("inMemoryUserRepository")
                    UserRepository userRepository,
            UserFactory userFactory) {
        this.userRepository = userRepository;
        this.userFactory = userFactory;
    }

    @Override
    public Result<?> updateUser(UserUpdateDto userUpdate) {
        if (userUpdate.getEmail().equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un email");
        }
        if (userUpdate.getFullName().equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un nom");
        }

        return userRepository.findUserWithEmail(userUpdate.getEmail())
                .filter(user -> !user.getId().getIdentity().equals(userUpdate.getIdentifiant().getId()))
                .map(found -> TransactionResult.asForbidden("Cet email existe déjà"))
                .orElseGet(() -> {
                    IdentifiantDto userIdDto = userUpdate.getIdentifiant();
                    return userRepository.get(userIdDto.getId())
                            .map(user -> {
                                userRepository.put(
                                        userFactory.updateUser(user)
                                                .withEmail(userUpdate.getEmail())
                                                .withPasswordHash(user.getPasswordHash())
                                                .withGender(Gender.valueOf(userUpdate.getGender()))
                                                .withFullName(userUpdate.getFullName())
                                                .update()
                                );
                                return TransactionResult.asSuccess();
                            })
                            .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
                });
    }
}
