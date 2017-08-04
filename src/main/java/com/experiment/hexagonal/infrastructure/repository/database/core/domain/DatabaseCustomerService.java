package com.experiment.hexagonal.infrastructure.repository.database.core.domain;

import com.experiment.hexagonal.infrastructure.repository.database.core.api.CrudDatabaseCustomer;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseCustomer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;

@Service("crudDatabaseCustomer")
public class DatabaseCustomerService implements CrudDatabaseCustomer {
    private final Set<DatabaseCustomer> datas;

    public DatabaseCustomerService() {
        this.datas = new HashSet<>();
    }

    @Override
    public Stream<DatabaseCustomer> stream() {
        return datas.stream();
    }

    @Override
    public DatabaseCustomer get(UUID id) {
        return stream()
                .filter(databaseCustomer -> databaseCustomer.getUuid().equals(id))
                .findFirst()
                .orElse(null);        
    }

    @Override
    public void put(DatabaseCustomer databaseCustomer) {        
        datas.add(databaseCustomer);
    }

    @Override
    public void remove(DatabaseCustomer databaseCustomer) {        
        datas.remove(databaseCustomer);
    }

    @Override
    public void clear() {
        datas.clear();
    }
    
    @Override
    public List<DatabaseCustomer> getForUser(UUID userId) {
        return stream()
                .filter(inMemoryCustomer -> inMemoryCustomer.getUserId().equals(userId))
                .collect(toList());
    }
    
    @Override
    public List<DatabaseCustomer> getForAdresse(UUID adresseId) {
        return stream()
                .filter(inMemoryCustomer -> inMemoryCustomer.getAdresseId().equals(adresseId))
                .collect(toList());
    }
}
