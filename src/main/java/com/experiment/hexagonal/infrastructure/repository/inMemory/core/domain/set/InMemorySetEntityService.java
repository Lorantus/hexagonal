package com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryEntity;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

@Service("crudInMemorySetUser")
public abstract class InMemorySetEntityService<T extends InMemoryEntity> {
    private final Set<T> datas;

    public InMemorySetEntityService() {
        this.datas = new HashSet<>();
    }

    public Stream<T> stream() {
        return datas.stream();
    }

    public void put(T inMemoryEntity) {        
        datas.add(inMemoryEntity);
    }

    public void remove(T inMemoryEntity) {        
        datas.remove(inMemoryEntity);
    }

    public void clear() {
        datas.clear();
    }
}
