package com.experiment.hexagonal.infrastructure.repository.database.core.api;

import com.experiment.hexagonal.infrastructure.repository.database.core.domain.DatabaseAdresseService;
import com.experiment.hexagonal.infrastructure.repository.database.core.model.DatabaseAdresse;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DatabaseAdresseTest {
    CrudDatabaseAdresse crudDatabaseAdresse = new DatabaseAdresseService();
        
    @Test
    public void doitRetournerUneAdresse() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        DatabaseAdresse databaseAdresse = new DatabaseAdresse(uuid);
        databaseAdresse.setVille("ville");
        crudDatabaseAdresse.put(databaseAdresse);
        
        // WHEN
        DatabaseAdresse adresse = crudDatabaseAdresse.get(uuid);
        
        // THEN
        assertThat(adresse).extracting(DatabaseAdresse::getVille)
                .isEqualTo("ville");
    }
    
    @Test
    public void doitRetournerNullSiUneAdresseNExistePas() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        
        // WHEN
        DatabaseAdresse adresse = crudDatabaseAdresse.get(uuid);
        
        // THEN
        assertThat(adresse).isNull();
    }
    
    @Test
    public void doitEffacerUneAdresse() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        DatabaseAdresse databaseAdresse = new DatabaseAdresse(uuid);
        crudDatabaseAdresse.put(databaseAdresse);
        
        // WHEN
        crudDatabaseAdresse.remove(databaseAdresse);
        
        // THEN
        assertThat(crudDatabaseAdresse.get(uuid)).isNull();
    }
}
