package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.*;

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
        UserCreateDto userCreate = unUtilisateur("email", "password", "X", "fullName");
        
        // WHEN
        Result result = createUser.createUser(userCreate);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);
    }
    
    @Test
    public void doitRetournerUneErreurSiCreerUnUtilisateurAvecEmailExistant() {
        // GIVEN
        createUser.createUser(unUtilisateur("email", "password", "X", "fullName"));
        
        // WHEN
        Result result = createUser.createUser(unUtilisateur("email", "password2", "MR", "fullName2"));
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);
    }

    private UserCreateDto unUtilisateur(String email, String password, String gender, String fullName) {
        UserCreateDto userCreate = new UserCreateDto();
        userCreate.setEmail(email);
        userCreate.setPasswordHash(password);
        userCreate.setGender(gender);
        userCreate.setFullName(fullName);
        return userCreate;
    }
}
