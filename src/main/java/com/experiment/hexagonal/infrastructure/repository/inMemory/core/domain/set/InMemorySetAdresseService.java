package com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryAdresse;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service("crudInMemorySetAdresse")
public class InMemorySetAdresseService extends InMemorySetEntityService<InMemoryAdresse> implements CrudInMemoryAdresse {

    @Override
    public InMemoryAdresse get(UUID id) {
        return stream()
                .filter(inMemortAdresse -> inMemortAdresse.getUuid().equals(id))
                .findFirst()
                .orElse(null);
    }
}
