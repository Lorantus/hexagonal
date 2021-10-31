package com.experiment.hexagonal.infrastructure.application.core.model;

import java.util.Objects;

public class ApplicationPassword {
    private final String rawValue;

    private ApplicationPassword(String rawValue) {
        this.rawValue = rawValue;
    }

    public static ApplicationPassword create(String rawValue) {
        return new ApplicationPassword(rawValue);
    }

    public String getRawPassword() {
        return rawValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationPassword other = (ApplicationPassword) o;
        return Objects.equals(rawValue, other.rawValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rawValue);
    }
}
