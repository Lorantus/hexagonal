package com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryCustomer;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryCustomer;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service("crudInMemorySetCustomer")
public class InMemorySetCustomerService extends InMemorySetEntityService<InMemoryCustomer> implements CrudInMemoryCustomer {

    @Override
    public InMemoryCustomer get(UUID id) {
        return stream()
                .filter(inMemoryCustomer -> inMemoryCustomer.getUuid().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public Collection<InMemoryCustomer> getForUser(UUID userId) {
        return stream()
                .filter(inMemoryCustomer -> inMemoryCustomer.getUserId().equals(userId))
                .collect(toList());
    }
    
    @Override
    public Collection<InMemoryCustomer> getForAdresse(UUID adresseId) {
        return stream()
                .filter(inMemoryCustomer -> inMemoryCustomer.getAdresseId().equals(adresseId))
                .collect(toList());
    }
}
