package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.model.IdentifiantDto;
import com.experiment.hexagonal.core.api.model.PasswordDto;
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

import static org.assertj.core.api.Assertions.assertThat;

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
        createUser.createUser(unUtilisateurACreer("email", "password", "X", "fullName"));
        UserUpdateDto userCreated = new UserUpdateDto();
        userCreated.setEmail("email");
        UserUpdateDto userToUpdate = findUser.findUserByEmail(userCreated).getData();        
        
        UserUpdateDto userUpdate = unUtilisateurAMettreAJour(userToUpdate.getIdentifiant(), "email corrigé", "MR", "fullName corrigé");
        
        // WHEN
        Result<?> result = updateUser.updateUser(userUpdate);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);
        
        UserUpdateDto userToFind = new UserUpdateDto();
        userToFind.setEmail("email corrigé");       
        assertThat(findUser.findUserByEmail(userToFind).getData())
                .extracting(
                        userFound -> userFound.getIdentifiant().getId(),
                        UserUpdateDto::getEmail,
                        UserUpdateDto::getGender,
                        UserUpdateDto::getFullName)
                .containsOnly(
                        userToUpdate.getIdentifiant().getId(),
                        "email corrigé",
                        "MR",
                        "fullName corrigé");
    }
    
    @Test
    public void doitRetournerUneErreurSiMiseAJourUnUtilisateurAvecEmailExistant() {
        // GIVEN        
        createUser.createUser(unUtilisateurACreer("email existant", "password", "X", "fullName"));
        
        createUser.createUser(unUtilisateurACreer("email", "password", "X", "fullName"));
        UserUpdateDto user = new UserUpdateDto();
        user.setEmail("email");
        UserUpdateDto userToUpdate = findUser.findUserByEmail(user).getData(); 
        
        UserUpdateDto userUpdate = unUtilisateurAMettreAJour(userToUpdate.getIdentifiant(), "email existant", "MR", "fullName corrigé");
        
        // WHEN
        Result<?> result = updateUser.updateUser(userUpdate);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);        
    }

    private UserCreateDto unUtilisateurACreer(String email, String password, String gender, String fullName) {
        UserCreateDto userCreate = new UserCreateDto();
        userCreate.setEmail(email);
        userCreate.setPasswordHash(new PasswordDto(password));
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
