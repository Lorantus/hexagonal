package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserBuilder;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static com.experiment.hexagonal.core.domain.ResultAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    public void doitMettreAJourUnUser() {
        // GIVEN
        User user = UserBuilder.buildNewUser("email", Password.create("password"), "développeur")
                .build();
        storeUser(user);

        UserUpdateDto userUpdate = new UserUpdateDto(IdentifiantDto.create(user.getId().getIdentity()), "email modifié", "fullName modifié");
        userUpdate.setGender("MR");

        sansUserPourLEmail("email modifié");

        // WHEN
        Result<?> result = userService.updateUser(userUpdate);

        // THEN
        assertThat(result).isSuccess();

        verify(userRepository).put(userCaptor.capture());
        Assertions.assertThat(userCaptor.getValue())
                .extracting(
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsOnly(
                        "email modifié",
                        Password.create("password"),
                        Gender.MR,
                        "fullName modifié");
    }

    @Test
    public void doitRetournerUneErreurSiLEmailExisteDejaLorsDeLaMiseAJourDeLUser() {
        // GIVEN        
        User user = UserBuilder.buildNewUser("email autre", Password.create("password"), "développeur")
                .build();
        storeUserByEmail(user);

        UserUpdateDto userUpdate = new UserUpdateDto(IdentifiantDto.create(UUID.randomUUID()), "email autre", "autre développeur");

        // WHEN
        Result<?> result = userService.updateUser(userUpdate);

        // THEN
        assertThat(result).isForbidden();
    }

    @Test
    public void doitMettreAJourLUserSiLEmailExisteDejaSurLeMemeUser() {
        // GIVEN        
        User user = UserBuilder.buildNewUser("email", Password.create("password"), "développeur")
                .build();
        storeUser(user);
        storeUserByEmail(user);

        UserUpdateDto userUpdate = new UserUpdateDto(IdentifiantDto.create(user.getId().getIdentity()), "email", "fullName modifié");
        userUpdate.setGender("MR");

        // WHEN
        Result<?> result = userService.updateUser(userUpdate);

        // THEN
        assertThat(result).isSuccess();

        verify(userRepository).put(userCaptor.capture());
        Assertions.assertThat(userCaptor.getValue())
                .extracting(
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsOnly(
                        "email",
                        Password.create("password"),
                        Gender.MR,
                        "fullName modifié");
    }


    private void sansUserPourLEmail(String email) {
        when(userRepository.findUserWithEmail(email))
                .thenReturn(Optional.empty());
    }

    private void storeUserByEmail(User user) {
        when(userRepository.findUserWithEmail(user.getEmail()))
                .thenReturn(Optional.of(user));
    }

    private void storeUser(User user) {
        when(userRepository.get(user.getId().getIdentity()))
                .thenReturn(Optional.of(user));
    }
}
