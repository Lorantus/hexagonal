package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.model.PasswordDto;
import com.experiment.hexagonal.core.api.model.UserCreateDto;

public class UserCreateDtoBuilder {
    private final String email;
    private final String password;
    private String gender;
    private String fullname;

    private UserCreateDtoBuilder(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCreateDtoBuilder builder(String email, String password) {
        return new UserCreateDtoBuilder(email, password);
    }

    public UserCreateDtoBuilder withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public UserCreateDtoBuilder withFullName(String fullName) {
        this.fullname = fullName;
        return this;
    }

    public UserCreateDto build() {
        UserCreateDto user = new UserCreateDto(email, new PasswordDto(password));
        user.setGender(gender);
        user.setFullName(fullname);
        return user;
    }
}