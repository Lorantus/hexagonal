package com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryCustomer;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryCustomer;

import java.util.*;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service("crudInMemoryMapCustomer")
public class InMemoryMapCustomerService implements CrudInMemoryCustomer {
    private final Map<UUID, InMemoryCustomer> datas;

    public InMemoryMapCustomerService() {
        this.datas = new HashMap<>();
    }

    @Override
    public Stream<InMemoryCustomer> stream() {
        return datas.values().stream();
    }

    @Override
    public InMemoryCustomer get(UUID id) {
        return datas.get(id);        
    }

    @Override
    public void put(InMemoryCustomer inMemoryCustomer) {        
        datas.put(inMemoryCustomer.getUuid(), inMemoryCustomer);
    }

    @Override
    public void remove(InMemoryCustomer inMemoryCustomer) {        
        datas.remove(inMemoryCustomer.getUuid());
    }

    @Override
    public void clear() {
        datas.clear();
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
