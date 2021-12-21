package com.experiment.hexagonal.core.factory;

import com.experiment.hexagonal.core.model.entity.User;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class UserAssert extends AbstractAssert<UserAssert, User> {
    protected UserAssert(User user) {
        super(user, UserAssert.class);
    }

    public static UserAssert assertThatUser(User user) {
        return new UserAssert(user);
    }

    public UserAssert isSameAs(User expected) {
        isNotNull();

        Assertions.assertThat(expected).isNotNull();

        Assertions.assertThat(actual.getId())
                .overridingErrorMessage("Expected id to be <%s> but was <%s>", expected.getId(), actual.getId())
                .isEqualTo(expected.getId());
        Assertions.assertThat(actual.getEmail())
                .overridingErrorMessage("Expected email to be <%s> but was <%s>", expected.getEmail(), actual.getEmail())
                .isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getFullName())
                .overridingErrorMessage("Expected fullname to be <%s> but was <%s>", expected.getFullName(), actual.getFullName())
                .isEqualTo(expected.getFullName());
        Assertions.assertThat(actual.getGender())
                .overridingErrorMessage("Expected gender to be <%s> but was <%s>", expected.getGender(), actual.getGender())
                .isEqualTo(expected.getGender());
        Assertions.assertThat(actual.getPasswordHash().getRawPassword())
                .overridingErrorMessage("Expected password to be <%s> but was <%s>", expected.getPasswordHash().getRawPassword(), actual.getPasswordHash().getRawPassword())
                .isEqualTo(expected.getPasswordHash().getRawPassword());

        return this;
    }
}
