package com.experiment.hexagonal.infrastructure.client.core.domain.http;

import com.experiment.hexagonal.infrastructure.application.core.model.ClientAdresse;
import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientAuthentification;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientCreateUser;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientCrudAdresse;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientDeleteUser;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientFindUserByEmail;
import com.experiment.hexagonal.infrastructure.client.core.spi.ClientUpdateUser;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HttpController {
    private final ClientAuthentification clientAuthentification;
    private final ClientCreateUser clientCreateUser;
    private final ClientUpdateUser clientUpdateUser;
    private final ClientDeleteUser clientDeleteUser;
    private final ClientFindUserByEmail clientFindUserByEmail;
    
    private final ClientCrudAdresse clientCrudAdresse;

    @Autowired
    public HttpController(
            ClientAuthentification clientAuthentification,
            ClientCreateUser clientCreateUser, ClientUpdateUser clientUpdateUser, ClientDeleteUser clientDeleteUser,
            ClientFindUserByEmail clientFindUserByEmail,
            ClientCrudAdresse clientCrudAdresse) {
        this.clientAuthentification = clientAuthentification;
        this.clientCreateUser = clientCreateUser;
        this.clientUpdateUser = clientUpdateUser;
        this.clientDeleteUser = clientDeleteUser;
        this.clientFindUserByEmail = clientFindUserByEmail;
        this.clientCrudAdresse = clientCrudAdresse;
    }
    
    public void createUser(String email, String password, String gender, String fullName) {
        clientCreateUser.setEmail(email);
        clientCreateUser.setPasswordHash(password);
        clientCreateUser.setGender(gender);
        clientCreateUser.setFullName(fullName);
        clientCreateUser.createUser();
    }
    
    public String findUserFullNameWithEmail(String email) {
        clientFindUserByEmail.setEmail(email);
        return clientFindUserByEmail.executeFindFullName();
    }
    
    public ClientUser findUserWithEmail(String email) {
        clientFindUserByEmail.setEmail(email);
        return clientFindUserByEmail.executeFind();
    }
    
    public void updateUser(UUID id, String email, String gender, String fullName) {
        clientUpdateUser.setId(id);
        clientUpdateUser.setEmail(email);
        clientUpdateUser.setGender(gender);
        clientUpdateUser.setFullName(fullName);
        clientUpdateUser.updateUser();
    }
    
    public void deleteUser(UUID id) {
        clientDeleteUser.setId(id);
        clientDeleteUser.deleteUser();
    }
    
    public void isAuthentife(String login, String password) {
        clientAuthentification.setLogin(login);
        clientAuthentification.setPasswordHash(password);
        clientAuthentification.isAuthentified();
    }
    
    public void createAdresse(String ville) {
        clientCrudAdresse.setVille(ville);
        clientCrudAdresse.createAdresse();
    }
    
    public ClientAdresse findAdresseWithVille(String ville) {
        clientCrudAdresse.setVille(ville);
        return clientCrudAdresse.executeFind();
    }
    
    public void updateAdresse(UUID id, String ville) {
        clientCrudAdresse.setIdentifiant(id);
        clientCrudAdresse.setVille(ville);
        clientCrudAdresse.updateAdresse();
    }
    
    public void deleteAdresse(UUID id) {
        clientCrudAdresse.setIdentifiant(id);
        clientCrudAdresse.deleteAdresse();
    }
}
