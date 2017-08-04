package com.experiment.hexagonal.infrastructure.repository.inMemory.core.model;

import java.util.UUID;
import java.util.stream.Stream;

public interface Repository<T> {
    Stream<T> stream();
    void put(T item);
    T get(UUID id);
    void remove(T item);
    void clear();
}
