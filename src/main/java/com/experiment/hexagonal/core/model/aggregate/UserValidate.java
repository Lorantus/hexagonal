package com.experiment.hexagonal.core.model.aggregate;

public class UserValidate {
    private final String email;
    private final String fullName;

    private UserValidate(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }

    public static UserValidate create(String email, String fullName) {
        return new UserValidate(email, fullName);
    }

    public void isValid() {
        if (email.equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un email");
        }
        if (fullName.equals("")) {
            throw new IllegalArgumentException("Un utilisateur doit avoir un nom");
        }
    }
}
