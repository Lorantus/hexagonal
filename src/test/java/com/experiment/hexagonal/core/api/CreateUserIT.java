package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
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
public class CreateUserIT {
    
    @Autowired
    private CreateUser createUser;
    
    @Autowired
    @Qualifier("inMemoryUserRepository")
    private UserRepository userRepository;
    
    @Before
    public void setUp() {
        userRepository.clear();
    }
    
    @Test
    public void doitCreerUnUtilisateur() {
        // GIVEN
        UserCreateDto user = unUtilisateur("email", "password");
        
        // WHEN
        Result<?> result = createUser.createUser(user);
        
        // THEN
        assertThat(result).isSuccess();
    }
    
    @Test
    public void doitRetournerUneErreurSiCreerUnUtilisateurAvecEmailExistant() {
        // GIVEN
        createUser.createUser(unUtilisateur("email-commun", "password"));
        UserCreateDto userEnDouble = unUtilisateur("email-commun", "autre-password");

        // WHEN
        Result<?> result = createUser.createUser(userEnDouble);

        // THEN
        assertThat(result).isForbidden();
    }


    private UserCreateDto unUtilisateur(String email, String password) {
        return UserCreateDtoBuilder.builder(email, password)
                .withFullName("fullName")
                .withGender("X")
                .build();
    }
}
