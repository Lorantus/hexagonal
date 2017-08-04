package com.experiment.hexagonal.infrastructure.repository.database.core.domain;

import com.experiment.hexagonal.infrastructure.repository.database.core.api.CrudDatabaseUser;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseUser;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service(value = "crudDatabaseUser")
public class DatabaseUserService implements CrudDatabaseUser {
    private final Set<DatabaseUser> datas;

    public DatabaseUserService() {
        this.datas = new HashSet<>();
    }

    @Override
    public Stream<DatabaseUser> stream() {
        return datas.stream();
    }

    @Override
    public DatabaseUser get(UUID id) {
        return stream()
                .filter(databaseUser -> databaseUser.getUuid().equals(id))
                .findFirst()
                .orElse(null);        
    }

    @Override
    public void put(DatabaseUser databaseUser) {        
        datas.add(databaseUser);
    }

    @Override
    public void remove(DatabaseUser databaseUser) {        
        datas.remove(databaseUser);
    }

    @Override
    public void clear() {
        datas.clear();
    }
}
