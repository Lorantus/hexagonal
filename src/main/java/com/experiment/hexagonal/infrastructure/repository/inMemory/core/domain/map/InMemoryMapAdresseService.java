package com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryAdresse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service("crudInMemoryMapAdresse")
public class InMemoryMapAdresseService implements CrudInMemoryAdresse {
    private final Map<UUID, InMemoryAdresse> datas;

    public InMemoryMapAdresseService() {
        this.datas = new HashMap<>();
    }

    @Override
    public Stream<InMemoryAdresse> stream() {
        return datas.values().stream();
    }

    @Override
    public InMemoryAdresse get(UUID id) {
        return datas.get(id);        
    }

    @Override
    public void put(InMemoryAdresse inMemoryUserAdresse) {        
        datas.put(inMemoryUserAdresse.getUuid(), inMemoryUserAdresse);
    }

    @Override
    public void remove(InMemoryAdresse inMemoryAdresse) {        
        datas.remove(inMemoryAdresse.getUuid());
    }

    @Override
    public void clear() {
        datas.clear();
    }
}
