package com.experiment.hexagonal.infrastructure.client.core.domain.http;

import com.experiment.hexagonal.AppConfigTest;
import com.experiment.hexagonal.core.api.CrudAdresse;
import com.experiment.hexagonal.core.domain.*;
import com.experiment.hexagonal.core.spi.AdresseRepository;
import com.experiment.hexagonal.core.spi.UserRepository;
import com.experiment.hexagonal.infrastructure.application.adapter.ApplicationCoreAdresseAdpateur;
import com.experiment.hexagonal.infrastructure.application.adapter.ApplicationCoreAuthentificationAdpateur;
import com.experiment.hexagonal.infrastructure.application.adapter.ApplicationCoreFindUserByEmailAdpateur;
import com.experiment.hexagonal.infrastructure.application.adapter.ApplicationCoreUserAdpateur;
import com.experiment.hexagonal.infrastructure.application.core.api.*;
import com.experiment.hexagonal.infrastructure.application.core.domain.*;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientAdresse;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;
import com.experiment.hexagonal.infrastructure.application.core.spi.APICrudAdresse;
import com.experiment.hexagonal.infrastructure.application.core.spi.APIFindByEmail;
import com.experiment.hexagonal.infrastructure.client.adapter.ClientApplicationAuthentificationAdpateur;
import com.experiment.hexagonal.infrastructure.client.adapter.ClientApplicationCrudAdresseAdpateur;
import com.experiment.hexagonal.infrastructure.client.adapter.ClientApplicationFindUserByEmailAdpateur;
import com.experiment.hexagonal.infrastructure.client.adapter.ClientApplicationUserAdpateur;
import com.experiment.hexagonal.infrastructure.client.core.domain.ClientUserAssert;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientAuthentification;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientCrudAdresse;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientFindUserByEmail;
import com.experiment.hexagonal.infrastructure.repository.database.adapter.DatabaseUserRepository;
import com.experiment.hexagonal.infrastructure.repository.database.core.api.CrudDatabaseUser;
import com.experiment.hexagonal.infrastructure.repository.database.core.domain.DatabaseUserService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.adapter.InMemoryAdresseRepository;
import com.experiment.hexagonal.infrastructure.repository.inMemory.adapter.InMemoryUserRepository;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryUser;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetAdresseService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
public class HttpControllerIT {
    @Autowired
    private HttpController controller;
    
    // @Before
    public void setUp() {
        CrudInMemoryUser crudInMemoryUser = new InMemorySetUserService();
        UserRepository inMemoryUserRepository = new InMemoryUserRepository(crudInMemoryUser);

        CrudDatabaseUser crudDatabaseUser = new DatabaseUserService();
        UserRepository databaseUserRepository = new DatabaseUserRepository(crudDatabaseUser);

        UserLoginService userLoginService = new UserLoginService(databaseUserRepository);

        CreateUserService createUser = new CreateUserService(inMemoryUserRepository);
        UpdateUserService updateUser = new UpdateUserService(inMemoryUserRepository);

        DeleteUserService deleteUser = new DeleteUserService(inMemoryUserRepository);
        FindUserService findUser = new FindUserService(inMemoryUserRepository);

        ApplicationCoreAuthentificationAdpateur apiAuthentification = new ApplicationCoreAuthentificationAdpateur(userLoginService);
        ApplicationCoreUserAdpateur apiUser = new ApplicationCoreUserAdpateur(createUser, updateUser, deleteUser);
        APIFindByEmail apiFindUserByEmail = new ApplicationCoreFindUserByEmailAdpateur(findUser);

        ApplicationAuthentification applicationAuthentification = new ClientAuthentificationService(apiAuthentification);
        ApplicationCreateUser applicationCreateUser = new ClientCreateUserService(apiUser);
        ApplicationUpdateUser applicationUpdateUser = new ClientUpdateUserService(apiUser);
        ApplicationDeleteUser applicationDeleteUser = new ClientDeleteUserService(apiUser);
        ApplicationFindUserByEmail applicationFindUserByEmail = new ClientFindUserByEmailService(apiFindUserByEmail);

        ClientAuthentification clientAuthentification = new ClientApplicationAuthentificationAdpateur(applicationAuthentification);
        ClientApplicationUserAdpateur applicationUserAdpateur = new ClientApplicationUserAdpateur(applicationCreateUser, applicationUpdateUser, applicationDeleteUser);
        ClientFindUserByEmail clientApplicationFindUserByEmail = new ClientApplicationFindUserByEmailAdpateur(applicationFindUserByEmail);

        CrudInMemoryAdresse crudInMemoryAdresse = new InMemorySetAdresseService();
        AdresseRepository adresseRepository = new InMemoryAdresseRepository(crudInMemoryAdresse);
        CrudAdresse crudAdresse = new AdresseService(adresseRepository);
        APICrudAdresse apiCrudAdresse = new ApplicationCoreAdresseAdpateur(crudAdresse);
        ApplicationCrudAdresse applicationCrudAdresse = new ClientCrudAdresseService(apiCrudAdresse);
        ClientCrudAdresse clientCrudAdresse = new ClientApplicationCrudAdresseAdpateur(applicationCrudAdresse);
                
        controller = new HttpController(
                clientAuthentification,
                applicationUserAdpateur,
                applicationUserAdpateur,
                applicationUserAdpateur,
                clientApplicationFindUserByEmail,
                clientCrudAdresse);
    }

    @Test
    public void doitCreerUnUser() {
        // WHEN
        controller.createUser("email", "password", "MR", "fullName");

        // THEN
        ClientUserAssert.assertThat(controller.findUserWithEmail("email"))
                .hasFullName("fullName");
    }

    @Test
    public void doitRetournerUnUserParSonEmail() {
        // THEN
        controller.createUser("email", "password", null, "fullName");

        // WHEN
        ClientUser clientUser = controller.findUserWithEmail("email");

        // THEN
        ClientUserAssert.assertThat(clientUser)
                .hasEmail("email")
                .hasFullName("fullName");
    }

    @Test
    public void doitRetournerNullSiUnUserNExistePas() {
        // THEN
        controller.createUser("email", "password", "MME", "fullName");
        
        // WHEN
        ClientUser clientUser = controller.findUserWithEmail("email incorrect");
        
        // THEN
        assertThat(clientUser).isNull();
    }

    @Test
    public void doitRetournerLeFullNameDUnUserParSonEmail() {
        // THEN
        controller.createUser("email", "password", "X", "fullName");
        
        // WHEN
        String found = controller.findUserFullNameWithEmail("email");
        
        // THEN
        assertThat(found).isEqualTo("fullName");
    }
    
    @Test
    public void doitMettreAJourUnUser() {
        // GIVEN
        controller.createUser("email", "password", "X", "fullName");
        ClientUser user = controller.findUserWithEmail("email");

        // WHEN
        controller.updateUser(user.getId(), "nouveau email", "MR", "nouveau fullName");

        // THEN
        assertThat(controller.findUserWithEmail("email")).isNull();

        ClientUserAssert.assertThat(controller.findUserWithEmail("nouveau email"))
                .hasGenderAndFullName("MR", "nouveau fullName");
    }
    
    @Test
    public void doitRetirerUnUser() {
        // GIVEN
        controller.createUser("email", "password", null, "fullName");
        ClientUser user = controller.findUserWithEmail("email");
                
        // WHEN
        controller.deleteUser(user.getId());
        
        // THEN
        assertThat(controller.findUserWithEmail("email")).isNull();
    }

    @Test
    public void doitCreerUneAdresse() {
        // WHEN
        controller.createAdresse("ville");

        // THEN
        assertThat(controller.findAdresseWithVille("ville"))
                .extracting(ClientAdresse::getVille)
                .isEqualTo("ville");
    }
    
    @Test
    public void doitMettreAJourUneAdresse() {
        // GIVEN
        controller.createAdresse("ville");
        ClientAdresse adresse = controller.findAdresseWithVille("ville");
                
        // WHEN
        controller.updateAdresse(adresse.getId(), "nouvelle adresse");
        
        // THEN
        assertThat(controller.findAdresseWithVille("ville")).isNull();

        assertThat(controller.findAdresseWithVille("nouvelle adresse"))
                .extracting(ClientAdresse::getVille)
                .isEqualTo("nouvelle adresse");
    }
    
    @Test
    public void doitRetirerUeAdresse() {
        // GIVEN
        controller.createAdresse("ville");
        ClientAdresse adresse = controller.findAdresseWithVille("ville");
                
        // WHEN
        controller.deleteAdresse(adresse.getId());
        
        // THEN
        assertThat(controller.findAdresseWithVille("ville")).isNull();
    }
}
