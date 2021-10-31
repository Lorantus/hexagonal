package com.experiment.hexagonal.infrastructure.repository.inMemory.core.api;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetAdresseService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.model.InMemoryAdresse;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CrudInMemoryAdresseTest {
    
    private final CrudInMemoryAdresse curdInMemoryAdresse = new InMemorySetAdresseService();
        
    @Test
    public void doitRetournerUneAdresse() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        InMemoryAdresse inMemoryAdresse = new InMemoryAdresse(uuid);
        inMemoryAdresse.setVille("ville");
        curdInMemoryAdresse.put(inMemoryAdresse);
        
        // WHEN
        InMemoryAdresse adresse = curdInMemoryAdresse.get(uuid);
        
        // THEN
        assertThat(adresse).extracting(InMemoryAdresse::getVille)
                .isEqualTo("ville");
    }
    
    @Test
    public void doitRetournerNullSiUneAdresseNExistePas() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        
        // WHEN
        InMemoryAdresse adresse = curdInMemoryAdresse.get(uuid);
        
        // THEN
        assertThat(adresse).isNull();
    }
    
    @Test
    public void doitEffacerUneAdresse() {
        // GIVEN
        UUID uuid = UUID.randomUUID();
        InMemoryAdresse inMemoryAdresse = new InMemoryAdresse(uuid);
        curdInMemoryAdresse.put(inMemoryAdresse);
        
        // WHEN
        curdInMemoryAdresse.remove(inMemoryAdresse);
        
        // THEN
        assertThat(curdInMemoryAdresse.get(uuid)).isNull();
    }
}
