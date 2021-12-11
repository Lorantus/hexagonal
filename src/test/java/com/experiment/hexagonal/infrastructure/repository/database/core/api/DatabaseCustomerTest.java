package com.experiment.hexagonal.infrastructure.repository.database.core.api;

import com.experiment.hexagonal.infrastructure.repository.database.core.domain.DatabaseAdresseService;
import com.experiment.hexagonal.infrastructure.repository.database.core.domain.DatabaseCustomerService;
import com.experiment.hexagonal.infrastructure.repository.database.core.domain.DatabaseUserService;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseAdresse;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseCustomer;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseUser;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseCustomerTest {
    private final CrudDatabaseUser crudDatabaseUser = new DatabaseUserService();
    private final CrudDatabaseAdresse crudDatabaseAdresse = new DatabaseAdresseService();
    
    private final CrudDatabaseCustomer crudDatabaseCustomer = new DatabaseCustomerService();
        
    @Test
    public void doitRetournerUnCustomer() {
        // GIVEN
        UUID userUuid = UUID.randomUUID();
        DatabaseUser inMemoryUser = new DatabaseUser(userUuid);
        crudDatabaseUser.put(inMemoryUser);
        
        UUID adresseUuid = UUID.randomUUID();
        DatabaseAdresse inMemoryAdresse = new DatabaseAdresse(adresseUuid);
        crudDatabaseAdresse.put(inMemoryAdresse);

        UUID customerUuid = UUID.randomUUID();
        DatabaseCustomer inMemoryCustomer = new DatabaseCustomer(customerUuid);
        inMemoryCustomer.setUserId(userUuid);
        inMemoryCustomer.setAdresseId(adresseUuid);
        crudDatabaseCustomer.put(inMemoryCustomer);

        // WHEN
        DatabaseCustomer customer = crudDatabaseCustomer.get(customerUuid);

        // THEN
        assertThat(customer)
                .extracting(DatabaseCustomer::getUserId, DatabaseCustomer::getAdresseId)
                .containsExactly(userUuid, adresseUuid);
    }
    
    @Test
    public void doitRetournerNullSiUnCustomerNExistePas() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        
        // WHEN
        DatabaseCustomer customer = crudDatabaseCustomer.get(uuid);
        
        // THEN
        assertThat(customer).isNull();
    }
    
    @Test
    public void doitEffacerUnCustomer() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        DatabaseCustomer inMemoryCustomer = new DatabaseCustomer(uuid);
        
        // WHEN
        crudDatabaseCustomer.remove(inMemoryCustomer);
        
        // THEN
        assertThat(crudDatabaseCustomer.get(uuid)).isNull();
    }
}
