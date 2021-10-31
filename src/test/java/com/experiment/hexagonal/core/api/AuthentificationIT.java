package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.model.AuthentificationDto;
import com.experiment.hexagonal.core.api.model.LoginDto;
import com.experiment.hexagonal.core.api.model.PasswordDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
public class AuthentificationIT {
    
    @Autowired
    private Authentification authentification;
    
    @Autowired
    private CreateUser createUser;
        
    @Test
    public void doitRetournerTrueSiAuthentificationReussie() {
        // GIVEN        
        unUtilisateur();

        AuthentificationDto authentificationObject = AuthentificationDto.create(new LoginDto("login"), new PasswordDto("password"));

        // WHEN
        boolean authentified = authentification.isAuthentified(authentificationObject);

        // THEN
        assertThat(authentified).isTrue();
    }

    @Test
    public void doitRetournerFalseSiAuthentificationEchoueSurEmail() {
        // GIVEN
        unUtilisateur();

        AuthentificationDto authentificationObject = AuthentificationDto.create(new LoginDto("login incorrect"), new PasswordDto("password"));

        // WHEN
        boolean authentified = authentification.isAuthentified(authentificationObject);

        // THEN
        assertThat(authentified).isFalse();
    }

    @Test
    public void doitRetournerFalseSiAuthentificationEchoueSurPassword() {
        // GIVEN
        unUtilisateur();

        AuthentificationDto authentificationObject = AuthentificationDto.create(new LoginDto("login"), new PasswordDto("password incorrect"));

        // WHEN
        boolean authentified = authentification.isAuthentified(authentificationObject);

        // THEN
        assertThat(authentified).isFalse();
    }

    private void unUtilisateur() {
        UserCreateDto userCreate = new UserCreateDto();
        userCreate.setEmail("login");
        userCreate.setPasswordHash(new PasswordDto("password"));
        userCreate.setFullName("développeur");
        userCreate.setGender("X");
        createUser.createUser(userCreate);
    }    
}
