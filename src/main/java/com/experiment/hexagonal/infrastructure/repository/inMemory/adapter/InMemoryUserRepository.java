package com.experiment.hexagonal.infrastructure.repository.inMemory.adapter;

import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryUser;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository("inMemoryUserRepository")
public class InMemoryUserRepository implements UserRepository {
    private static final Function<InMemoryUser, User> USER_MAPPER = inMemoryUser -> {
        User user = new User(
                UserId.create(inMemoryUser.getId()),
                inMemoryUser.getEmail(),
                Password.create(inMemoryUser.getPasswordHash()),
                inMemoryUser.getFullName());
        user.setGender(inMemoryUser.getGender() == null ? null : Gender.valueOf(inMemoryUser.getGender()));
        return user;
    };

    private final CrudInMemoryUser crudInMemoryUser;
    
    @Autowired
    public InMemoryUserRepository(@Qualifier(value = "crudInMemorySetUser") CrudInMemoryUser crudInMemoryUser) {
        this.crudInMemoryUser = crudInMemoryUser;
    }
    
    private Optional<InMemoryUser> findUserWith(Predicate<InMemoryUser> predicate) {
        return crudInMemoryUser.stream()
                .filter(predicate)
                .findFirst();
    }
    
    private Optional<InMemoryUser> findInMemoryUser(UUID id) {
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
        return findInMemoryUser(id)
                .map(USER_MAPPER);
    }

    @Override
    public void put(User user) {
        InMemoryUser inMemoryUser = findInMemoryUser(user.getId().getIdentity())
                .orElse(new InMemoryUser(UUID.randomUUID()));
        inMemoryUser.setId(user.getId().getIdentity().toString());
        inMemoryUser.setEmail(user.getEmail());
        inMemoryUser.setPasswordHash(user.getPasswordHash().getRawPassword());
        inMemoryUser.setGender(user.getGender() == null ? null : user.getGender().name());
        inMemoryUser.setFullName(user.getFullName());
        crudInMemoryUser.put(inMemoryUser);
    }

    @Override
    public void remove(User user) {
        findInMemoryUser(user.getId().getIdentity())
                .ifPresent(crudInMemoryUser::remove);
    }

    @Override
    public void clear() {
        crudInMemoryUser.clear();
    }
}
