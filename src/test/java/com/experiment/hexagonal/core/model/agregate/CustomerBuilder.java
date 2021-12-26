package com.experiment.hexagonal.core.model.agregate;

import com.experiment.hexagonal.core.factory.UserBuilder;
import com.experiment.hexagonal.core.model.aggregate.Customer;

public class CustomerBuilder {
    private UserBuilder userBuilder;
    private AdressBuilder adressBuilder;

    private CustomerBuilder() {
        // Constructeur privé
    }

    public static CustomerBuilder creatCustomerBuilder() {
        return new CustomerBuilder();
    }

    public CustomerBuilder withUser(UserBuilder userBuilder) {
        this.userBuilder = userBuilder;
        return this;
    }

    public CustomerBuilder withAdresse(AdressBuilder adressBuilder) {
        this.adressBuilder = adressBuilder;
        return this;
    }

    public Customer build() {
        return new Customer(userBuilder.build(), adressBuilder.build());
    }
}
