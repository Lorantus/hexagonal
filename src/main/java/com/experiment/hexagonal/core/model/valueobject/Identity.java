package com.experiment.hexagonal.core.model.valueobject;

import java.util.Objects;

public class Identity<T> {
    private final T id;
    
    protected Identity(T id) {
        this.id = id;
    }

    public T getIdentity() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Identity<?> other = (Identity<?>) obj;
        return Objects.equals(this.id, other.id);
    }
    
}
