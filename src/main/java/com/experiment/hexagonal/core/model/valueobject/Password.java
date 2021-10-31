package com.experiment.hexagonal.core.model.valueobject;

import java.util.Objects;

public class Password {
    private final String rawValue;

    private Password(String rawValue) {
        this.rawValue = rawValue;
    }

    public static Password create(String rawValue) {
        return new Password(rawValue);
    }

    public String getRawPassword() {
        return rawValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password other = (Password) o;
        return Objects.equals(rawValue, other.rawValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawValue);
    }
}
