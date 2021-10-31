package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.Authentification;
import com.experiment.hexagonal.core.api.model.AuthentificationDto;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService implements Authentification {
    private final UserRepository userRepository;

    @Autowired
    public UserLoginService(@Qualifier("inMemoryUserRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isAuthentified(AuthentificationDto authentificationDto) {
        Password password = Password.create(authentificationDto.getPasswordHash().getRawValue());
        return userRepository.findUserWithEmail(authentificationDto.getLogin().getLoginValue())
                .map(User::getPasswordHash)
                .filter(passwordHash -> passwordHash.equals(password))
                .isPresent();
    }
}