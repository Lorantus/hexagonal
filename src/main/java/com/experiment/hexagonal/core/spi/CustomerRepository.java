package com.experiment.hexagonal.core.spi;

import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import java.util.Collection;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> get(User user, Adresse adresse);    
    void put(Customer userAdresse);
    void remove(Customer userAdresse);

    Collection<Customer> getByUser(User user);
    Collection<Customer> getByAdresse(Adresse adresse);
}
