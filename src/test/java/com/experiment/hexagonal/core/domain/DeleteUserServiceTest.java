package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.factory.UserBuilder;
import com.experiment.hexagonal.core.model.entity.User;
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

import static com.experiment.hexagonal.core.domain.ResultAssert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteUserService userService;

    @Captor
    private ArgumentCaptor<User> argument;

    @Test
    public void doitEffacerUnUser() {
        // GIVEN   
        User user = UserBuilder.buildNewUser("login", Password.create("password"), "développeur").build();
        when(userRepository.get(eq(user.getId().getIdentity())))
                .thenReturn(Optional.of(user));

        IdentifiantDto identifiantDto = IdentifiantDto.create(user.getId().getIdentity());

        // WHEN
        Result<?> result = userService.deleteUser(identifiantDto);

        // THEN
        assertThat(result).isSuccess();

        verify(userRepository).remove(argument.capture());
        Assertions.assertThat(argument.getValue())
                .extracting(userDeleted -> userDeleted.getId().getIdentity())
                .isEqualTo(identifiantDto.getId());
    }
}
