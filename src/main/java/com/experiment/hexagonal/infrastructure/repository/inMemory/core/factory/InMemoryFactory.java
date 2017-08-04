package com.experiment.hexagonal.infrastructure.repository.inMemory.core.factory;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryAdresse;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryCustomer;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.api.CrudInMemoryUser;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map.InMemoryMapAdresseService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map.InMemoryMapCustomerService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map.InMemoryMapUserService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetAdresseService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetCustomerService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetUserService;
import java.util.function.Supplier;

public class InMemoryFactory {
    private final Mode mode;
    
    public InMemoryFactory(Mode mode) {
        this.mode = mode;
    }
    
    public CrudInMemoryUser createUser() {
        return mode.crudInMemoryUserSupplier.get();
    }
    
    public CrudInMemoryAdresse createAdresse() {
        return mode.crudInMemoryAdresseSupplier.get();
    }
    
    public CrudInMemoryCustomer createCustomer() {
        return mode.crudInMemoryCustomerSupplier.get();
    }
    
    public enum Mode {
        MAP(
            InMemoryMapUserService::new,
            InMemoryMapAdresseService::new,
            InMemoryMapCustomerService::new
        ), 
        SET(
            InMemorySetUserService::new,
            InMemorySetAdresseService::new,
            InMemorySetCustomerService::new
        );
        
        private final Supplier<CrudInMemoryUser> crudInMemoryUserSupplier;
        private final Supplier<CrudInMemoryAdresse> crudInMemoryAdresseSupplier;
        private final Supplier<CrudInMemoryCustomer> crudInMemoryCustomerSupplier;
        
        Mode(Supplier<CrudInMemoryUser> crudInMemoryUserSupplier,
             Supplier<CrudInMemoryAdresse> crudInMemoryAdresseSupplier,
             Supplier<CrudInMemoryCustomer> crudInMemoryCustomerSupplier) {
            this.crudInMemoryUserSupplier = crudInMemoryUserSupplier;
            this.crudInMemoryAdresseSupplier = crudInMemoryAdresseSupplier;
            this.crudInMemoryCustomerSupplier = crudInMemoryCustomerSupplier;
        }
    }
}
