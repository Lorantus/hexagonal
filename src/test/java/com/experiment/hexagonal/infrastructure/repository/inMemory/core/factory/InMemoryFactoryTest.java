package com.experiment.hexagonal.infrastructure.repository.inMemory.core.factory;

import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map.InMemoryMapAdresseService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map.InMemoryMapCustomerService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.map.InMemoryMapUserService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetAdresseService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetCustomerService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.domain.set.InMemorySetUserService;
import com.experiment.hexagonal.infrastructure.repository.inMemory.core.factory.InMemoryFactory.Mode;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import static org.assertj.core.api.Assertions.*;

@RunWith(value = Parameterized.class)
public class InMemoryFactoryTest {
    
    //default value = 0
    @Parameter(value = 0)
    public Mode mode;
    
    @Parameter(value = 1)
    public Class[] values;
    
    @Parameters(name = "{index}: doitRetournerLesClassesDeGestion({0}) = {2}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Mode.MAP, new Class[] { 
                    InMemoryMapUserService.class, 
                    InMemoryMapAdresseService.class,
                    InMemoryMapCustomerService.class
                }},
                {Mode.SET, new Class[] { 
                    InMemorySetUserService.class, 
                    InMemorySetAdresseService.class,
                    InMemorySetCustomerService.class
                }}
        });
    }

    @Test
    public void doitRetournerLesClassesDeGestion() {
        //WHEN
        InMemoryFactory inMemoryFactory = new InMemoryFactory(mode);
        
        // THEN
        assertThat(inMemoryFactory.createUser()).isInstanceOf(values[0]);
        assertThat(inMemoryFactory.createAdresse()).isInstanceOf(values[1]);
    }
}
