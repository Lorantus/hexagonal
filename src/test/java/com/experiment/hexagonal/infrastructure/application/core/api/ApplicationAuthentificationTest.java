package com.experiment.hexagonal.infrastructure.application.core.api;

import com.experiment.hexagonal.AppConfigTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
public class ApplicationAuthentificationTest {
    
    @Autowired
    private ApplicationAuthentification applicationAuthentification;
    
    @Autowired
    private ApplicationCreateUser applicationCreateUser;
    
    @Test
    public void doitDefinirLelogin() {
        // GIVEN
        ReflectionTestUtils.setField(applicationAuthentification, "login", "");
        
        // WHEN
        applicationAuthentification.setLogin("login");
                
        // THEN
        assertThat(ReflectionTestUtils.getField(applicationAuthentification, "login")).isEqualTo("login");
    }
    
    @Test
    public void doitDefinirLePassword() {
        // GIVEN
        ReflectionTestUtils.setField(applicationAuthentification, "passwordHash", "");
        
        // WHEN
        applicationAuthentification.setPasswordHash("passwordHash");
                
        // THEN
        assertThat(ReflectionTestUtils.getField(applicationAuthentification, "passwordHash")).isEqualTo("passwordHash");
    }

    @Test
    public void doitRetournerTrueSiAuthentificationReussie() {
        // GIVEN        
        unUtilisateur("login", "password");
        
        applicationAuthentification.setLogin("login");
        applicationAuthentification.setPasswordHash("password");
        
        // WHEN
        boolean authentified = applicationAuthentification.isAuthentified();
        
        // THEN
        assertThat(authentified).isTrue();
    }
    
    @Test
    public void doitRetournerTrueSiAuthentificationEchoueSurEmail() {
        // GIVEN
        unUtilisateur("login", "password");
        
        applicationAuthentification.setLogin("login incorrect");
        applicationAuthentification.setPasswordHash("password");
        
        // WHEN
        boolean authentified = applicationAuthentification.isAuthentified();
        
        // THEN
        assertThat(authentified).isFalse();
    }
    
    @Test
    public void doitRetournerTrueSiAuthentificationEchoueSurPassword() {
        // GIVEN
        unUtilisateur("login", "password");
        
        applicationAuthentification.setLogin("login");
        applicationAuthentification.setPasswordHash("password incorrect");
        
        // WHEN
        boolean authentified = applicationAuthentification.isAuthentified();
        
        // THEN
        assertThat(authentified).isFalse();
    }

    private void unUtilisateur(String login, String password) {
        applicationCreateUser.setEmail(login);
        applicationCreateUser.setPasswordHash(password);
        applicationCreateUser.execute();
    }    
}
