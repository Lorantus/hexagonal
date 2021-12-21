package com.experiment.hexagonal.core.factory;

import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import org.junit.Test;

import java.util.UUID;

import static com.experiment.hexagonal.core.factory.UserAssert.assertThatUser;
import static org.assertj.core.api.Assertions.assertThat;

public class UserBuilderTest {
    
    @Test
    public void doitRetournerUnUtilisateur() {
        // GIVEN
        UserId userId = UserId.create(UUID.randomUUID());
        
        // WHEN
        User user = UserBuilder.buildUser(userId, "email", Password.create("password"), "fullName")
                .withGender(Gender.MR)
                .build();
        
        // THEN
        User expected = new User(userId, "email", Password.create("password"), "fullName");
        expected.setGender(Gender.MR);

        assertThatUser(user)
                .isSameAs(expected);
    }

    @Test
    public void doitRetournerUnNouveauUtilisateur() {
        // GIVEN
        UserBuilder newUser = UserBuilder.buildNewUser("login", Password.create("password"), "développeur");
                
        // WHEN
        User user = newUser.build();
        
        // THEN
        assertThat(user.getId()).isNotNull();
    }
}
