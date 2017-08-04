package com.experiment.hexagonal.infrastructure.repository.database.core.model;

import java.util.UUID;
import java.util.stream.Stream;

public interface CrudDatabase<T> {
    Stream<T> stream();
    void put(T databaseEntity);
    T get(UUID id);
    void remove(T databaseEntity);
    void clear();
}
