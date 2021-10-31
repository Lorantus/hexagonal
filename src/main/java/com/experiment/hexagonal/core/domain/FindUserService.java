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
    public Result<UserUpdateDto> findUserByEmail(UserUpdateDto userEmail) {
        return userRepository.findUserWithEmail(userEmail.getEmail())
                .map(user -> {
                    UserUpdateDto userResult = new UserUpdateDto();
                    IdentifiantDto identifiantDto = IdentifiantDto.create(user.getId().getIdentity());
                    userResult.setIdentifiant(identifiantDto);
                    userResult.setEmail(user.getEmail());
                    userResult.setGender(user.getGender().name());
                    userResult.setFullName(user.getFullName());
                    return TransactionResult.asSuccess(userResult);
                })
                .orElse(TransactionResult.asBadRequest("L'user n'existe pas"));
    }
}
