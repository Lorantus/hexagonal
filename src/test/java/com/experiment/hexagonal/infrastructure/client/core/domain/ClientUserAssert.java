package com.experiment.hexagonal.infrastructure.client.core.domain;

import com.experiment.hexagonal.infrastructure.application.core.model.ClientUser;
import org.assertj.core.api.AbstractAssert;

public class ClientUserAssert extends AbstractAssert<ClientUserAssert, ClientUser> {

    private ClientUserAssert(ClientUser actual) {
        super(actual, ClientUserAssert.class);
    }

    public static ClientUserAssert assertThat(ClientUser actual) {
        return new ClientUserAssert(actual);
    }

    public ClientUserAssert hasGenderAndFullName(String gender, String fullName) {
        isNotNull();
        if (!(actual.getGender().equals(gender) && actual.getFullName().equals(fullName))) {
            failWithMessage("Expected person to have full name %s %s but was %s %s",
                    gender, fullName, actual.getGender(), actual.getFullName());
        }
        return this;
    }

    public ClientUserAssert hasEmail(String email) {
        isNotNull();
        if (!actual.getEmail().equals(email)) {
            failWithMessage("Expected person to have email %s but was %s",
                    email, actual.getEmail());
        }
        return this;
    }

    public ClientUserAssert hasFullName(String fullName) {
        isNotNull();
        if (!actual.getFullName().equals(fullName)) {
            failWithMessage("Expected person to have email %s but was %s",
                    fullName, actual.getFullName());
        }
        return this;
    }
}