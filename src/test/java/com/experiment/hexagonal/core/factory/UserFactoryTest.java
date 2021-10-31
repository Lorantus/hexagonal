package com.experiment.hexagonal.core.factory;

import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.entity.UserId;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserFactoryTest {
    
    @Test
    public void doitRetournerUnUtilisateur() {
        // GIVEN
        UserId userId = UserId.create(UUID.randomUUID());
        
        // WHEN
        User user = new UserFactory()
                .buildUser(userId, "email", "fullName")
                .withPasswordHash(Password.create("password"))
                .withGender(Gender.MR)
                .build();
        
        // THEN
        assertThat(user)
                .extracting(
                        User::getId,
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsExactly(
                        userId,
                        "email",
                        Password.create("password"),
                        Gender.MR,
                        "fullName");
    }

    @Test
    public void doitRetournerUnNouveauUtilisateur() {
        // GIVEN
        UserFactory.UserBuilder newUser = new UserFactory().buildNewUser("login", "développeur");
                
        // WHEN
        User user = newUser.build();
        
        // THEN
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void doitRetournerUnUtilisateurAJour() {
        // GIVEN
        UserId userId = UserId.create(UUID.randomUUID());
        User user = new UserFactory()
                .buildUser(userId, "email", "fullName")
                .withPasswordHash(Password.create("password"))
                .withGender(Gender.MR)
                .build();
        
        // WHEN
        User userUpdate = new UserFactory()
                .updateUser(user)
                .withEmail("email modifié")
                .withPasswordHash(Password.create("password modifié"))
                .withGender(Gender.MME)
                .withFullName("fullName modifié")
                .update();
        
        // THEN
        assertThat(userUpdate).isEqualTo(user);
        assertThat(userUpdate)
                .extracting(
                        User::getId,
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsExactly(
                        userId,
                        "email modifié",
                        Password.create("password modifié"),
                        Gender.MME,
                        "fullName modifié");
    }
    
}
