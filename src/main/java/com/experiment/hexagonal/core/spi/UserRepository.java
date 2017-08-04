package com.experiment.hexagonal.core.spi;

import com.experiment.hexagonal.core.model.entity.User;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> get(UUID id);
    void put(User user);
    void remove(User user);
    Optional<User> findUserWithEmail(String email);
    void clear();
}