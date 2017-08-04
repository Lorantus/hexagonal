package com.experiment.hexagonal.infrastructure.repository.inMemory.core.api;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetUserService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryUser;
import java.util.UUID;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CrudInMemoryUserTest {
    
    private final CrudInMemoryUser crudInMemoryUser = new InMemorySetUserService();
        
    @Test
    public void doitRetournerUnUser() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        InMemoryUser inMemoryUser = new InMemoryUser(uuid);
        inMemoryUser.setEmail("email");
        inMemoryUser.setPasswordHash("password");
        inMemoryUser.setGender("gender");
        inMemoryUser.setFullName("fullname");
        crudInMemoryUser.put(inMemoryUser);
        
        // WHEN
        InMemoryUser adresse = crudInMemoryUser.get(uuid);
        
        // THEN
        assertThat(adresse).extracting(
                    InMemoryUser::getEmail,
                    InMemoryUser::getPasswordHash,
                    InMemoryUser::getGender,
                    InMemoryUser::getFullName)
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
        InMemoryUser adresse = crudInMemoryUser.get(uuid);
        
        // THEN
        assertThat(adresse).isNull();
    }
    
    @Test
    public void doitEffacerUneUser() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        InMemoryUser inMemoryUser = new InMemoryUser(uuid);
        crudInMemoryUser.put(inMemoryUser);
        
        // WHEN
        crudInMemoryUser.remove(inMemoryUser);
        
        // THEN
        assertThat(crudInMemoryUser.get(uuid)).isNull();
    }
}
