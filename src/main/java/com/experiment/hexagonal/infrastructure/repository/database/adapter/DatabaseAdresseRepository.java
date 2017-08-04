package com.experiment.hexagonal.infrastructure.repository.database.adapter;

import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.spi.AdresseRepository;
import com.experiment.hexagonal.infrastructure.repository.database.core.api.CrudDatabaseAdresse;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseAdresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository("databaseAdresseRepository")
public class DatabaseAdresseRepository implements AdresseRepository {
    private static final Function<DatabaseAdresse, Adresse> ADRESSE_MAPPER = databaseAdresse -> {
                    Adresse adresse = Adresse.create(databaseAdresse.getId());
                    adresse.setVille(databaseAdresse.getVille());
                    return adresse;
                };
    
    private final CrudDatabaseAdresse crudDatabaseAdresse;

    @Autowired
    public DatabaseAdresseRepository(CrudDatabaseAdresse crudDatabaseAdresse) {
        this.crudDatabaseAdresse = crudDatabaseAdresse;
    }
    
    private Optional<DatabaseAdresse> findAdresseWith(Predicate<DatabaseAdresse> predicate) {
        return crudDatabaseAdresse.stream()
                .filter(predicate)
                .findFirst();
    }
    
    private Optional<DatabaseAdresse> findDatabaseAdresse(UUID id) {
        String inMermoryId = id.toString();
        return findAdresseWith(inMemoryAdresse -> inMemoryAdresse.getId().equals(inMermoryId));
    }

    @Override
    public Optional<Adresse> findAdresseWithVille(String ville) {
        return findAdresseWith(inMemoryAdresse -> inMemoryAdresse.getVille().equals(ville))
                .map(ADRESSE_MAPPER);
    }

    @Override
    public void clear() {
        crudDatabaseAdresse.clear();
    }

    @Override
    public Optional<Adresse> get(UUID id) {
        return findDatabaseAdresse(id)
                .map(ADRESSE_MAPPER);
    }

    @Override
    public void put(Adresse adresse) {
        DatabaseAdresse inMemoryAdresse = findDatabaseAdresse(adresse.getIdentity())
                .orElse(new DatabaseAdresse(UUID.randomUUID()));
        inMemoryAdresse.setId(adresse.getIdentity().toString());
        inMemoryAdresse.setVille(adresse.getVille());
        crudDatabaseAdresse.put(inMemoryAdresse);
    }

    @Override
    public void remove(Adresse adresse) {
        findDatabaseAdresse(adresse.getIdentity())
                .ifPresent(crudDatabaseAdresse::remove);
    }
}
