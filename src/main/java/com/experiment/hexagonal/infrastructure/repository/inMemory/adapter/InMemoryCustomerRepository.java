package com.experiment.hexagonal.infrastructure.repository.inMemory.adapter;

import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.spi.AdresseRepository;
import com.experiment.hexagonal.core.spi.CustomerRepository;
import com.experiment.hexagonal.core.spi.UserRepository;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryCustomer;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryUser;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryCustomer;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryUser;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import static java.util.Collections.EMPTY_LIST;
import static java.util.stream.Collectors.toList;

@Repository("inMemoryCustomerRepository")
public class InMemoryCustomerRepository implements CustomerRepository {
    private final CrudInMemoryUser crudInMemoryUser; 
    private final CrudInMemoryAdresse crudInMemoryAdresse;
    private final CrudInMemoryCustomer crudInMemoryCustomer;
    
    private final Function<InMemoryCustomer, Customer> CUSTOMER_MAPPER;
    
    @Autowired
    public InMemoryCustomerRepository(
            @Qualifier(value = "inMemoryUserRepository") UserRepository userRepository,
            @Qualifier(value = "inMemoryAdresseRepository") AdresseRepository adresseRepository,
            @Qualifier(value = "crudInMemorySetAdresse") CrudInMemoryAdresse crudInMemoryAdresse,
            @Qualifier(value = "crudInMemorySetCustomer") CrudInMemoryCustomer crudInMemoryCustomer,
            @Qualifier(value = "crudInMemorySetUser") CrudInMemoryUser crudInMemoryUser) {
        this.crudInMemoryUser = crudInMemoryUser; 
        this.crudInMemoryAdresse = crudInMemoryAdresse;
        this.crudInMemoryCustomer = crudInMemoryCustomer;
        
        this.CUSTOMER_MAPPER = inMemoryCustomer -> {
            User user = userRepository.get(inMemoryCustomer.getUserId()).orElse(null);
            Adresse adresse = adresseRepository.get(inMemoryCustomer.getAdresseId()).orElse(null);
            return new Customer(user, adresse);
        };
    }
    
    private Optional<InMemoryCustomer> findCustomerWith(Predicate<InMemoryCustomer> predicate) {
        return crudInMemoryCustomer.stream()
                .filter(predicate)
                .findFirst();
    }
    
    private Optional<InMemoryCustomer> findInMemoryCustomer(User user, Adresse adresse) {
        UUID userId = user.getId().getIdentity();
        UUID adresseId = adresse.getIdentity();
        return findCustomerWith(inMemoryCustomer -> 
                    inMemoryCustomer.getUserId().equals(userId) && inMemoryCustomer.getAdresseId().equals(adresseId));
    }

    @Override
    public Optional<Customer> get(User user, Adresse adresse) {
        return findInMemoryCustomer(user, adresse)
                .map(CUSTOMER_MAPPER);
    }

    @Override
    public void put(Customer customer) {
        InMemoryCustomer inMemoryCustomer = findInMemoryCustomer(customer.getUser(), customer.getAdresse())
                .orElse(new InMemoryCustomer(UUID.randomUUID()));
        inMemoryCustomer.setUserId(customer.getUser().getId().getIdentity());
        inMemoryCustomer.setAdresseId(customer.getAdresse().getIdentity());
        crudInMemoryCustomer.put(inMemoryCustomer);
    }

    @Override
    public void remove(Customer customer) {
        findInMemoryCustomer(customer.getUser(), customer.getAdresse())
                .ifPresent(crudInMemoryCustomer::remove);
    }

    @Override
    public Collection<Customer> getByUser(User user) {
        InMemoryUser inMermoryUser = crudInMemoryUser.get(user.getId().getIdentity());
        return null == inMermoryUser ?  EMPTY_LIST :
                crudInMemoryCustomer.stream()
                .filter(inMemoryCustomer -> inMemoryCustomer.getUserId().equals(inMermoryUser.getUuid()))
                .map(CUSTOMER_MAPPER)
                .collect(toList());
    }

    @Override
    public Collection<Customer> getByAdresse(Adresse adresse) {
        InMemoryAdresse inMemoryAdresse = crudInMemoryAdresse.get(adresse.getIdentity());
        return null == inMemoryAdresse ?  EMPTY_LIST :
                crudInMemoryCustomer.stream()
                .filter(inMemoryCustomer -> inMemoryCustomer.getUserId().equals(inMemoryAdresse.getUuid()))
                .map(CUSTOMER_MAPPER)
                .collect(toList());
    }
}
