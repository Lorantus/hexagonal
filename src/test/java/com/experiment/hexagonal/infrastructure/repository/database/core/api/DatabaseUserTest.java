package com.experiment.hexagonal.infrastructure.repository.database.core.api;

import com.experiment.hexagonal.infrastructure.repository.database.core.api.CrudDatabaseUser;
import com.experiment.hexagonal.infrastructure.repository.database.core.domain.DatabaseUserService;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseUser;
import java.util.UUID;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseUserTest {
    CrudDatabaseUser crudDatabaseUser = new DatabaseUserService();
        
    @Test
    public void doitRetournerUneUser() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        DatabaseUser databaseUser = new DatabaseUser(uuid);
        databaseUser.setEmail("email");
        databaseUser.setPasswordHash("password");
        databaseUser.setGender("gender");
        databaseUser.setFullName("fullname");
        crudDatabaseUser.put(databaseUser);
        
        // WHEN
        DatabaseUser adresse = crudDatabaseUser.get(uuid);
        
        // THEN
        assertThat(adresse).extracting(
                    DatabaseUser::getEmail,
                    DatabaseUser::getPasswordHash,
                    DatabaseUser::getGender,
                    DatabaseUser::getFullName)
                .containsExactly(
                    "email",
                    "password",
                    "gender",
                    "fullname");
    }
    
    @Test
    public void doitRetournerNullSiUneUserNExistePas() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        
        // WHEN
        DatabaseUser adresse = crudDatabaseUser.get(uuid);
        
        // THEN
        assertThat(adresse).isNull();
    }
    
    @Test
    public void doitEffacerUneUser() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        DatabaseUser databaseUser = new DatabaseUser(uuid);
        crudDatabaseUser.put(databaseUser);
        
        // WHEN
        crudDatabaseUser.remove(databaseUser);
        
        // THEN
        assertThat(crudDatabaseUser.get(uuid)).isNull();
    }
}
