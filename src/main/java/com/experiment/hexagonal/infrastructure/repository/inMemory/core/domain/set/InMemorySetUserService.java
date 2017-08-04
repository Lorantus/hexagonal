package com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryUser;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryUser;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service("crudInMemorySetUser")
public class InMemorySetUserService extends InMemorySetEntityService<InMemoryUser> implements CrudInMemoryUser {

    @Override
    public InMemoryUser get(UUID id) {
        return stream()
                .filter(inMemoryUser -> inMemoryUser.getUuid().equals(id))
                .findFirst()
                .orElse(null);
    }
}
