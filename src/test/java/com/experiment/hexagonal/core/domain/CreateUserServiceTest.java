package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserBuilder;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.valueobject.Gender;
import com.experiment.hexagonal.core.model.valueobject.Password;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static com.experiment.hexagonal.core.domain.ResultAssert.assertThatResult;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateUserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    @Test
    public void doitCreerUnUtilisateur() {
        // GIVEN
        when(userRepository.findUserWithEmail(any()))
                .thenReturn(Optional.empty());

        UserCreateDto userCreate = UserCreateDtoBuilder.builder("email", "password")
                .withFullName("fullName")
                .withGender("X")
                .build();

        // WHEN
        Result<?> result = userService.createUser(userCreate);

        // THEN
        assertThatResult(result).isSuccess();

        verify(userRepository).put(userCaptor.capture());
        assertThat(userCaptor.getValue())
                .extracting(
                        User::getEmail,
                        User::getPasswordHash,
                        User::getGender,
                        User::getFullName)
                .containsOnly(
                        "email",
                        Password.create("password"),
                        Gender.X,
                        "fullName");
    }

    @Test
    public void doitRetournerUneErreurSiCreerUnUtilisateurAvecEmailExistant() {
        // GIVEN
        User user = UserBuilder.buildNewUser("login", Password.create("password"), "développeur")
                .build();
        when(userRepository.findUserWithEmail(any()))
                .thenReturn(Optional.of(user));

        UserCreateDto userCreate = UserCreateDtoBuilder.builder("login", "password")
                .withFullName("autre développeur")
                .build();

        // WHEN
        Result<?> result = userService.createUser(userCreate);

        // THEN
        assertThatResult(result).isForbidden();
    }
}
