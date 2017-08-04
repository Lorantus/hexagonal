package com.experiment.hexagonal.infrastructure.repository.database.core.api;

import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseCustomer;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.Repository;
import java.util.List;
import java.util.UUID;

public interface CrudDatabaseCustomer extends Repository<DatabaseCustomer> {
    List<DatabaseCustomer> getForUser(UUID userId);
    List<DatabaseCustomer> getForAdresse(UUID adresseId);
}
