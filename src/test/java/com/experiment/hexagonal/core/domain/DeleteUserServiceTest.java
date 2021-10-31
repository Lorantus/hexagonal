package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.core.factory.UserFactory;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeleteUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Spy
    private final UserFactory userFactory = new UserFactory();

    @InjectMocks
    private DeleteUserService userService;

    @Captor
    private ArgumentCaptor<User> argument;

    @Test
    public void doitEffacerUnUser() {
        // GIVEN   
        User user = userFactory.buildNewUser("login", "développeur").build();
        when(userRepository.get(eq(user.getId().getIdentity())))
                .thenReturn(Optional.of(user));

        UserUpdateDto userToDelete = new UserUpdateDto();
        userToDelete.setIdentifiant(IdentifiantDto.create(user.getId().getIdentity()));

        // WHEN
        Result<?> result = userService.deleteUser(userToDelete);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);

        verify(userRepository).remove(argument.capture());
        assertThat(argument.getValue())
                .extracting(userDeleted -> userDeleted.getId().getIdentity())
                .isEqualTo(userToDelete.getIdentifiant().getId());
    }
}
