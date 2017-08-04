package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
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
        createUser.createUser(unUtilisateurACreer("email", "password", "X", "fullName"));
        UserUpdateDto userCreated = new UserUpdateDto();
        userCreated.setEmail("email");
        UserUpdateDto userToDelete = findUser.findUserByEmail(userCreated).getData();        
        
        UserUpdateDto userUpdate = unUtilisateurAMettreAJour(userToDelete.getIdentifiant(), "", "", "");
        
        // WHEN
        Result result = deleteUser.deleteUser(userUpdate);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);
        assertThat(findUser.findUserByEmail(userCreated).getData()).isNull();
    }

    private UserCreateDto unUtilisateurACreer(String email, String password, String gender, String fullName) {
        UserCreateDto userCreate = new UserCreateDto();
        userCreate.setEmail(email);
        userCreate.setPasswordHash(password);
        userCreate.setGender(gender);
        userCreate.setFullName(fullName);
        return userCreate;
    }

    private UserUpdateDto unUtilisateurAMettreAJour(IdentifiantDto id, String email, String gender, String fullName) {
        UserUpdateDto userUpdate = new UserUpdateDto();
        userUpdate.setIdentifiant(id);
        userUpdate.setEmail(email);
        userUpdate.setGender(gender);
        userUpdate.setFullName(fullName);
        return userUpdate;
    }
}
