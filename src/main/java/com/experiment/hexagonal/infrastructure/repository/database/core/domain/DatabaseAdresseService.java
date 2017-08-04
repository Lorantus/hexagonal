package com.experiment.hexagonal.infrastructure.repository.database.core.domain;

import com.experiment.hexagonal.infrastructure.repository.database.core.api.CrudDatabaseAdresse;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseAdresse;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service(value = "crudDatabaseAdresse")
public class DatabaseAdresseService implements CrudDatabaseAdresse {
    private final Set<DatabaseAdresse> datas;

    public DatabaseAdresseService() {
        this.datas = new HashSet<>();
    }

    @Override
    public Stream<DatabaseAdresse> stream() {
        return datas.stream();
    }

    @Override
    public DatabaseAdresse get(UUID id) {
        return stream()
                .filter(databaseAdresse -> databaseAdresse.getUuid().equals(id))
                .findFirst()
                .orElse(null);        
    }

    @Override
    public void put(DatabaseAdresse databaseAdresse) {        
        datas.add(databaseAdresse);
    }

    @Override
    public void remove(DatabaseAdresse databaseAdresse) {        
        datas.remove(databaseAdresse);
    }

    @Override
    public void clear() {
        datas.clear();
    }
}
