package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.CrudUserAdresse;
import com.experiment.hexagonal.core.api.FindUserAdresse;
import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.spi.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomerService implements CrudUserAdresse, FindUserAdresse {
    private final CustomerRepository customerRepository;
    
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    @Override
    public Result<?> createCustomer(User user, Adresse adresse) {
        return customerRepository.get(user, adresse)
                .map(found -> TransactionResult.asForbidden("Ce Customer existe déjà"))
                .orElseGet(() -> {
                    customerRepository.put(new Customer(user, adresse));
                    return TransactionResult.asSuccess();
                });
    }

    @Override
    public Result<?> updateUser(Customer customer, User user) {
        return customerRepository.get(user, customer.getAdresse())
                .map(found -> TransactionResult.asForbidden("Ce Customer existe déjà pour User donnée"))
                .orElseGet(() -> customerRepository.get(customer.getUser(), customer.getAdresse())
                        .map(found -> {
                            customerRepository.put(found.withUser(user));
                            customerRepository.remove(found);
                            return TransactionResult.asSuccess();
                        })
                        .orElse(TransactionResult.asBadRequest("Ce Customer n'existe pas")));
    }

    @Override
    public Result<?> updateAdresse(Customer customer, Adresse adresse) {
        return customerRepository.get(customer.getUser(), adresse)
                .map(found -> TransactionResult.asForbidden("Ce Customer existe déjà pour Adresse donnée"))
                .orElseGet(() -> customerRepository.get(customer.getUser(), customer.getAdresse())
                        .map(found -> {
                            customerRepository.put(found.withAdresse(adresse));
                            customerRepository.remove(found);
                            return TransactionResult.asSuccess();
                        })
                        .orElse(TransactionResult.asBadRequest("Ce Customer n'existe pas")));
    }

    @Override
    public Result<?> deleteCustomer(User user, Adresse adresse) {
        return customerRepository.get(user, adresse)
                .map(found -> {
                    customerRepository.remove(found);
                    return TransactionResult.asSuccess();
                })
                .orElse(TransactionResult.asBadRequest("Ce Customer existe déjà"));
    }
    
    @Override
    public Collection<Customer> findByUser(User user) {
        return customerRepository.getByUser(user);
    }

    @Override
    public Collection<Customer> findByAdresse(Adresse adresse) {
        return customerRepository.getByAdresse(adresse);
    }
}
