package com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryUser;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryUser;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service("crudInMemoryMapUser")
public class InMemoryMapUserService implements CrudInMemoryUser {
    private final Map<UUID, InMemoryUser> datas;

    public InMemoryMapUserService() {
        this.datas = new HashMap<>();
    }

    @Override
    public Stream<InMemoryUser> stream() {
        return datas.values().stream();
    }

    @Override
    public InMemoryUser get(UUID id) {
        return datas.get(id);        
    }

    @Override
    public void put(InMemoryUser inMemoryUser) {        
        datas.put(inMemoryUser.getUuid(), inMemoryUser);
    }

    @Override
    public void remove(InMemoryUser inMemoryUser) {        
        datas.remove(inMemoryUser.getUuid());
    }

    @Override
    public void clear() {
        datas.clear();
    }
}
