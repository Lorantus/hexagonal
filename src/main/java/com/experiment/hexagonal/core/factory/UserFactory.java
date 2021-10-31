package com.experiment.hexagonal.core.factory;

import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import org.springframework.stereotype.Service;

@Service
public class UserFactory {

    public UserBuilder buildUser(UserId userId, String email, String fullName) {
        return new UserBuilder(userId)
                .withEmail(email)
                .withFullName(fullName)
                .withGender(Gender.X);
    }

    public UserBuilder buildNewUser(String email, String fullName) {
        return buildUser(UserId.randomId(), email, fullName);
    }

    public UserBuilder updateUser(User user) {
        return new UserBuilder(user);
    }

    public static class UserBuilder {
        private User user;
        private UserId userId;
        private String email;
        private Password passwordHash;
        private Gender gender;
        private String fullName;

        private UserBuilder(UserId userId) {
            this.userId = userId;
        }

        private UserBuilder(User user) {
            this.user = user;
        }
        
        public UserBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder withPasswordHash(Password passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public UserBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        
        private User update(User user) {
            user.setEmail(email);
            user.setEmail(email);
            user.setPasswordHash(passwordHash);
            user.setGender(gender);
            user.setFullName(fullName);
            return user;
        }
        
        public User build() {
            return update(new User(userId));
        }
        
        public User update() {
            return update(user);
        }
    }
}
