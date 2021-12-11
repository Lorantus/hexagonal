package com.experiment.hexagonal.core.factory;

import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;

public class UserBuilder {
    private final UserId userId;
    private final String email;
    private final Password passwordHash;
    private final String fullName;
    private Gender gender;

    private UserBuilder(UserId userId, String email, Password passwordHash, String fullName) {
        this.userId = userId;
        this.email = email;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
    }

    public static UserBuilder buildUser(UserId userId, String email, Password passwordHash, String fullName) {
        return new UserBuilder(userId, email, passwordHash, fullName)
                .withGender(Gender.X);
    }

    public static UserBuilder buildNewUser(String email, Password passwordHash, String fullName) {
        return buildUser(UserId.randomId(), email, passwordHash, fullName);
    }

    public UserBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public User build() {
        User user = new User(userId, email, passwordHash, fullName);
        user.setGender(gender);
        return user;
    }
}
