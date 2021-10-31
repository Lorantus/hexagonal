package com.experiment.hexagonal.infrastructure.repository.database.adapter;

import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import com.experiment.hexagonal.infrastructure.repository.database.core.api.CrudDatabaseUser;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository("databaseUserRepository")
public class DatabaseUserRepository implements UserRepository {
    private static final Function<DatabaseUser, User> USER_MAPPER = databaseUser -> {
                    User user = new User(UserId.create(databaseUser.getId()));
        user.setEmail(databaseUser.getEmail());
        user.setPasswordHash(Password.create(databaseUser.getPasswordHash()));
        user.setGender(databaseUser.getGender() == null ? null : Gender.valueOf(databaseUser.getGender()));
                    user.setFullName(databaseUser.getFullName());
                    return user;
                };
    
    private final CrudDatabaseUser crudDatabaseUser;

    @Autowired
    public DatabaseUserRepository(CrudDatabaseUser crudDatabaseUser) {
        this.crudDatabaseUser = crudDatabaseUser;
    }
    
    private Optional<DatabaseUser> findUserWith(Predicate<DatabaseUser> predicate) {
        return crudDatabaseUser.stream()
                .filter(predicate)
                .findFirst();
    }
    
    private Optional<DatabaseUser> findDatabaseUser(UUID id) {
        String inMermoryId = id.toString();
        return findUserWith(inMemoryUser -> inMemoryUser.getId().equals(inMermoryId));
    }

    @Override
    public Optional<User> findUserWithEmail(String email) {
        return findUserWith(inMemoryUser -> inMemoryUser.getEmail().equals(email))
                .map(USER_MAPPER);
    }

    @Override
    public Optional<User> get(UUID id) {
        return findDatabaseUser(id)
                .map(USER_MAPPER);
    }

    @Override
    public void put(User user) {
        DatabaseUser inMemoryUser = findDatabaseUser(user.getId().getIdentity())
                .orElse(new DatabaseUser(UUID.randomUUID()));
        inMemoryUser.setId(user.getId().getIdentity().toString());
        inMemoryUser.setEmail(user.getEmail());
        inMemoryUser.setPasswordHash(user.getPasswordHash().getRawPassword());
        inMemoryUser.setFullName(user.getFullName());
        crudDatabaseUser.put(inMemoryUser);
    }

    @Override
    public void remove(User user) {
        findDatabaseUser(user.getId().getIdentity())
                .ifPresent(crudDatabaseUser::remove);
    }

    @Override
    public void clear() {
        crudDatabaseUser.clear();
    }
}
