package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.PasswordDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import com.experiment.hexagonal.core.api.model.UserUpdateDto;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.spi.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.experiment.hexagonal.core.domain.ResultAssert.assertThatResult;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
public class UpdateUserIT {
    
    @Autowired
    private UpdateUser updateUser;

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
    public void doitMettreAJourUnUtilisateur() {
        // GIVEN
        unUtilisateur("email", "password", "X", "fullName");
        UserUpdateDto userToUpdate = findUser.findUserByEmail("email").getData();

        UserUpdateDto userUpdate = unUtilisateurAMettreAJour(userToUpdate.getIdentifiant(), "email corrigé", "MR", "fullName corrigé");

        // WHEN
        Result<?> result = updateUser.updateUser(userUpdate);

        // THEN
        assertThatResult(result).isSuccess();
    }
    
    @Test
    public void doitRetournerUneErreurSiMiseAJourUnUtilisateurAvecEmailExistant() {
        // GIVEN        
        unUtilisateur("email existant", "password", "X", "fullName");

        unUtilisateur("email", "password", "X", "fullName");
        UserUpdateDto userToUpdate = findUser.findUserByEmail("email").getData();

        UserUpdateDto userUpdate = unUtilisateurAMettreAJour(userToUpdate.getIdentifiant(), "email existant", "MR", "fullName corrigé");

        // WHEN
        Result<?> result = updateUser.updateUser(userUpdate);

        // THEN
        assertThatResult(result).isForbidden();
    }


    private void unUtilisateur(String email, String password, String gender, String fullName) {
        UserCreateDto userCreate = new UserCreateDto(email, new PasswordDto(password));
        userCreate.setGender(gender);
        userCreate.setFullName(fullName);
        createUser.createUser(userCreate);
    }

    private UserUpdateDto unUtilisateurAMettreAJour(IdentifiantDto id, String email, String gender, String fullName) {
        UserUpdateDto userUpdate = new UserUpdateDto(id, email, fullName);
        userUpdate.setGender(gender);
        return userUpdate;
    }
}
