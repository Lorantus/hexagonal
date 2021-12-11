package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.domain.UserCreateDtoBuilder;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.experiment.hexagonal.core.domain.ResultAssert.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
public class DeleteUserIT {

    @Autowired
    private DeleteUser deleteUser;

    @Autowired
    private CreateUser createUser;
    
    @Autowired
    private FindUser findUser;
    
    @Autowired
    @Qualifier("inMemoryUserRepository")
    private UserRepository userRepository;
    
    @Before
    public void setUp() {
        userRepository.clear();
    }
    
    @Test
    public void doitEffacerUnUtilisateur() {
        // GIVEN
        unUtilisateur("email");
        UserUpdateDto userToDelete = findUser.findUserByEmail("email").getData();

        // WHEN
        Result<?> result = deleteUser.deleteUser(userToDelete.getIdentifiant());

        // THEN
        assertThat(result).isSuccess();
    }


    private void unUtilisateur(String email) {
        createUser.createUser(UserCreateDtoBuilder.builder(email, "password")
                .withFullName("fullName")
                .withGender("X")
                .build());
    }
}
