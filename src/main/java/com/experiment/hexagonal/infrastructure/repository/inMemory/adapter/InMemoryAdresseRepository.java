package com.experiment.hexagonal.infrastructure.repository.inMemory.adapter;

import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.spi.AdresseRepository;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryAdresse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

@Repository("inMemoryAdresseRepository")
public class InMemoryAdresseRepository implements AdresseRepository {
    private static final Function<InMemoryAdresse, Adresse> ADRESSE_MAPPER = inMemoryAdresse -> {
                    Adresse adresse = Adresse.create(inMemoryAdresse.getId());
                    adresse.setVille(inMemoryAdresse.getVille());
                    return adresse;
                };

    private final CrudInMemoryAdresse crudInMemoryAdresse;
    
    @Autowired
    public InMemoryAdresseRepository(@Qualifier(value = "crudInMemorySetAdresse") CrudInMemoryAdresse crudInMemoryAdresse) {
        this.crudInMemoryAdresse = crudInMemoryAdresse;
    }
    
    private Optional<InMemoryAdresse> findAdresseWith(Predicate<InMemoryAdresse> predicate) {
        return crudInMemoryAdresse.stream()
                .filter(predicate)
                .findFirst();
    }
    
    private Optional<InMemoryAdresse> findInMemoryAdresse(UUID id) {
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
        crudInMemoryAdresse.clear();
    }

    @Override
    public Optional<Adresse> get(UUID id) {
        return findInMemoryAdresse(id)
                .map(ADRESSE_MAPPER);
    }

    @Override
    public void put(Adresse adresse) {
        InMemoryAdresse inMemoryAdresse = findInMemoryAdresse(adresse.getIdentity())
                .orElse(new InMemoryAdresse(UUID.randomUUID()));
        inMemoryAdresse.setId(adresse.getIdentity().toString());
        inMemoryAdresse.setVille(adresse.getVille());
        crudInMemoryAdresse.put(inMemoryAdresse);
    }

    @Override
    public void remove(Adresse adresse) {
        findInMemoryAdresse(adresse.getIdentity())
                .ifPresent(crudInMemoryAdresse::remove);
    }
}
