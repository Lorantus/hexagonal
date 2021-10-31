package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.core.factory.UserFactory;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    private final UserFactory userFactory = new UserFactory();

    @InjectMocks
    private UpdateUserService userService;

    @Captor
    private ArgumentCaptor<User> argument;

    @Test
    public void doitMettreAJourUnUser() {
        // GIVEN
        User user = userFactory.buildNewUser("login", "développeur")
                .withEmail("email")
                .withPasswordHash(Password.create("password"))
                .build();
        when(userRepository.get(eq(user.getId().getIdentity())))
                .thenReturn(Optional.of(user));

        UserUpdateDto userUpdate = new UserUpdateDto();
        userUpdate.setIdentifiant(IdentifiantDto.create(user.getId().getIdentity()));
        userUpdate.setEmail("email modifié");
        userUpdate.setGender("MR");
        userUpdate.setFullName("fullName modifié");

        when(userRepository.findUserWithEmail(eq("email modifié")))
                .thenReturn(Optional.empty());

        // WHEN
        Result<?> result = userService.updateUser(userUpdate);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);

        verify(userRepository).put(argument.capture());
        assertThat(argument.getValue())
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
        User user = userFactory.buildNewUser("login", "développeur")
                .withPasswordHash(Password.create("password"))
                .build();
        when(userRepository.findUserWithEmail(eq("email autre")))
                .thenReturn(Optional.of(user));

        UserUpdateDto userUpdate = new UserUpdateDto();
        userUpdate.setIdentifiant(IdentifiantDto.create(UUID.randomUUID()));
        userUpdate.setEmail("email autre");
        userUpdate.setFullName("autre développeur");

        // WHEN
        Result<?> result = userService.updateUser(userUpdate);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);
    }

    @Test
    public void doitMettreAJourLUserSiLEmailExisteDejaSurLeMemeUser() {
        // GIVEN        
        User user = userFactory.buildNewUser("login", "développeur")
                .withEmail("email")
                .withPasswordHash(Password.create("password"))
                .build();
        when(userRepository.get(eq(user.getId().getIdentity())))
                .thenReturn(Optional.of(user));

        UserUpdateDto userUpdate = new UserUpdateDto();
        userUpdate.setIdentifiant(IdentifiantDto.create(user.getId().getIdentity()));
        userUpdate.setEmail("email");
        userUpdate.setGender("MR");
        userUpdate.setFullName("fullName modifié");

        when(userRepository.findUserWithEmail(eq("email")))
                .thenReturn(Optional.of(user));

        // WHEN
        Result<?> result = userService.updateUser(userUpdate);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);

        verify(userRepository).put(argument.capture());
        assertThat(argument.getValue())
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
}
