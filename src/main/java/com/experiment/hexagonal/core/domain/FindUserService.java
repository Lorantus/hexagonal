package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.FindUser;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FindUserService implements FindUser {
    private final UserRepository userRepository;

    @Autowired
    public FindUserService(@Qualifier("inMemoryUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Result<UserUpdateDto> findUserByEmail(String email) {
        return userRepository.findUserWithEmail(email)
                .map(user -> {
                    IdentifiantDto identifiantDto = IdentifiantDto.create(user.getId().getIdentity());
                    UserUpdateDto userResult = new UserUpdateDto(identifiantDto, user.getEmail(), user.getFullName());
                    userResult.setGender(user.getGender().name());
                    return TransactionResult.asSuccess(userResult);
                })
                .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
    }
}
