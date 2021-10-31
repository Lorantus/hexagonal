package com.experiment.hexagonal.infrastructure.application.core.model;

import java.util.Objects;

public class AuthentificationPrincipal {
    private final ApplicationLogin applicationLogin;
    private final ApplicationPassword applicationPassword;

    private AuthentificationPrincipal(ApplicationLogin applicationLogin, ApplicationPassword applicationPassword) {
        this.applicationLogin = applicationLogin;
        this.applicationPassword = applicationPassword;
    }

    public static AuthentificationPrincipal create(String login, String password) {
        ApplicationLogin applicationLogin = ApplicationLogin.create(login);
        ApplicationPassword applicationPassword = ApplicationPassword.create(password);
        return new AuthentificationPrincipal(applicationLogin, applicationPassword);
    }

    public ApplicationLogin getLogin() {
        return applicationLogin;
    }

    public ApplicationPassword getPassword() {
        return applicationPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthentificationPrincipal other = (AuthentificationPrincipal) o;
        return Objects.equals(applicationLogin, other.applicationLogin) && Objects.equals(applicationPassword, other.applicationPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationLogin, applicationPassword);
    }
}
