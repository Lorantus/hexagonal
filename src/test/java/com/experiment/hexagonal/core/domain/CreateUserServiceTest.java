package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.PasswordDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    private final UserFactory userFactory = new UserFactory();

    @InjectMocks
    private CreateUserService userService;

    @Captor
    private ArgumentCaptor<User> argument;

    @Test
    public void doitCreerUnUtilisateur() {
        // GIVEN
        when(userRepository.findUserWithEmail(any()))
                .thenReturn(Optional.empty());

        UserCreateDto userCreate = new UserCreateDto();
        userCreate.setEmail("email");
        userCreate.setPasswordHash(new PasswordDto("password"));
        userCreate.setGender("X");
        userCreate.setFullName("fullName");

        // WHEN
        Result<?> result = userService.createUser(userCreate);

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
                        Gender.X,
                        "fullName");
    }

    @Test
    public void doitRetournerUneErreurSiCreerUnUtilisateurAvecEmailExistant() {
        // GIVEN
        User user = userFactory.buildNewUser("login", "développeur")
                .build();
        when(userRepository.findUserWithEmail(any()))
                .thenReturn(Optional.of(user));

        UserCreateDto userCreate = new UserCreateDto();
        userCreate.setEmail("login");
        userCreate.setFullName("autre développeur");

        // WHEN
        Result<?> result = userService.createUser(userCreate);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);

        verify(userRepository, never()).put(any(User.class));
    }
}
