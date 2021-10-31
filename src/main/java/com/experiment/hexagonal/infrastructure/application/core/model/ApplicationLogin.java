package com.experiment.hexagonal.infrastructure.application.core.model;

import java.util.Objects;

public class ApplicationLogin {
    private final String value;

    private ApplicationLogin(String value) {
        this.value = value;
    }

    public static ApplicationLogin create(String login) {
        return new ApplicationLogin(login);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationLogin other = (ApplicationLogin) o;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
