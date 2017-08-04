package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import java.util.Collection;

public interface FindUserAdresse {
    Collection<Customer> findByUser(User user);
    Collection<Customer> findByAdresse(Adresse adresse);
}