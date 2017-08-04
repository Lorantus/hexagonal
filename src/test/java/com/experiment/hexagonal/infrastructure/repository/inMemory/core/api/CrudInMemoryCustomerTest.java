package com.experiment.hexagonal.infrastructure.repository.inMemory.core.api;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetAdresseService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetCustomerService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetUserService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryCustomer;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryUser;
import java.util.UUID;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CrudInMemoryCustomerTest {
    private final CrudInMemoryUser curdInMemoryUser = new InMemorySetUserService();
    private final CrudInMemoryAdresse curdInMemoryAdresse = new InMemorySetAdresseService();
    
    private final CrudInMemoryCustomer curdInMemoryCustomer = new InMemorySetCustomerService();
        
    @Test
    public void doitRetournerUnCustomer() {
        // GIVEN
        UUID userUuid = UUID.randomUUID();
        InMemoryUser inMemoryUser = new InMemoryUser(userUuid);
        curdInMemoryUser.put(inMemoryUser);
        
        UUID adresseUuid = UUID.randomUUID();
        InMemoryAdresse inMemoryAdresse = new InMemoryAdresse(adresseUuid);
        curdInMemoryAdresse.put(inMemoryAdresse);
        
        UUID customerUuid = UUID.randomUUID();
        InMemoryCustomer inMemoryCustomer = new InMemoryCustomer(customerUuid);
        inMemoryCustomer.setUserId(userUuid);
        inMemoryCustomer.setAdresseId(adresseUuid);
        curdInMemoryCustomer.put(inMemoryCustomer);
        
        // WHEN
        InMemoryCustomer customer = curdInMemoryCustomer.get(customerUuid);
        
        // THEN
        assertThat(customer).extracting(InMemoryCustomer::getUserId, InMemoryCustomer::getAdresseId)
                .containsExactly(userUuid, adresseUuid);
    }
    
    @Test
    public void doitRetournerNullSiUnCustomerNExistePas() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        
        // WHEN
        InMemoryCustomer customer = curdInMemoryCustomer.get(uuid);
        
        // THEN
        assertThat(customer).isNull();
    }
    
    @Test
    public void doitEffacerUnCustomer() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        InMemoryCustomer inMemoryCustomer = new InMemoryCustomer(uuid);
        
        // WHEN
        curdInMemoryCustomer.remove(inMemoryCustomer);
        
        // THEN
        assertThat(curdInMemoryCustomer.get(uuid)).isNull();
    }
}
