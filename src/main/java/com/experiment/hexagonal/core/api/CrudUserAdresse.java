package com.experiment.hexagonal.core.api;

import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;

public interface CrudUserAdresse {
    Result createCustomer(User user, Adresse adresse);
    Result updateUser(Customer userAdresse, User user);
    Result updateAdresse(Customer userAdresse, Adresse adresse);
    Result deleteCustomer(User user, Adresse adresse);
}