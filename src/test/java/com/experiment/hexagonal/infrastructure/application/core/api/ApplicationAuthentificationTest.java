package com.experiment.hexagonal.infrastructure.application.core.api;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.infrastructure.application.core.model.AuthentificationPrincipal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
public class ApplicationAuthentificationTest {
    
    @Autowired
    private ApplicationAuthentification applicationAuthentification;
    
    @Autowired
    private ApplicationCreateUser applicationCreateUser;

    @Test
    public void doitRetournerTrueSiAuthentificationReussie() {
        // GIVEN
        unUtilisateur();

        AuthentificationPrincipal authentification = AuthentificationPrincipal.create("login", "password");

        // WHEN
        boolean authentified = applicationAuthentification.isAuthentified(authentification);

        // THEN
        assertThat(authentified).isTrue();
    }
    
    @Test
    public void doitRetournerTrueSiAuthentificationEchoueSurEmail() {
        // GIVEN
        AuthentificationPrincipal authentification = AuthentificationPrincipal.create("login incorrect", "password");
        
        // WHEN
        boolean authentified = applicationAuthentification.isAuthentified(authentification);
        
        // THEN
        assertThat(authentified).isFalse();
    }
    
    @Test
    public void doitRetournerTrueSiAuthentificationEchoueSurPassword() {
        // GIVEN
        unUtilisateur();

        AuthentificationPrincipal authentification = AuthentificationPrincipal.create("login", "password incorrect");

        // WHEN
        boolean authentified = applicationAuthentification.isAuthentified(authentification);

        // THEN
        assertThat(authentified).isFalse();
    }

    private void unUtilisateur() {
        applicationCreateUser.setEmail("login");
        applicationCreateUser.setPasswordHash("password");
        applicationCreateUser.setFullName("développeur");
        applicationCreateUser.setGender("X");
        applicationCreateUser.execute();
    }
}
