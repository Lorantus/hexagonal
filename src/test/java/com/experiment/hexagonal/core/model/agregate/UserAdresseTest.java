package com.experiment.hexagonal.core.model.agregate;

import com.experiment.hexagonal.core.factory.UserFactory;
import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import java.util.UUID;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAdresseTest {
    
    private final UserFactory userFactory = new UserFactory();

    @Test
    public void doitRetournerUserAdresseAvecUnNouveauUtilisateur() {
        // GIVEN
        User user = userFactory.buildNewUser().build();
        Adresse adresse = Adresse.create(UUID.randomUUID());
        
        Customer customer = new Customer(user, adresse);
        
        User nouveauUser = userFactory.buildNewUser()
                .withEmail("email")
                .build();
        
        // WHEN
        Customer newUserAdresse = customer.withUser(nouveauUser);
        
        // THEN
        assertThat(newUserAdresse.getUser()).isEqualTo(nouveauUser);
        assertThat(newUserAdresse.getAdresse()).isEqualTo(adresse);
    }

    @Test
    public void doitRetournerUserAdresseAvecUneNouvelleAdresse() {
        // GIVEN
        User user = userFactory.buildNewUser().build();
        Adresse adresse = Adresse.create(UUID.randomUUID());
        
        Customer customer = new Customer(user, adresse);
        
        Adresse nouvelleAdresse = Adresse.randomId();
        nouvelleAdresse.setVille("ville");
        
        // WHEN
        Customer newUserAdresse = customer.withAdresse(nouvelleAdresse);
        
        // THEN
        assertThat(newUserAdresse.getUser()).isEqualTo(user);
        assertThat(newUserAdresse.getAdresse()).isEqualTo(nouvelleAdresse);
    }

    @Test
    public void testEquals() {
        // GIVEN
        User user = userFactory.buildNewUser().build();
        Adresse adresse = Adresse.create(UUID.randomUUID());
        
        Customer customer = new Customer(user, adresse);
        
        // WHEN - THEN
        assertThat(customer.equals(new Customer(user, adresse))).isTrue();
        
        assertThat(customer.equals(new Customer(userFactory.buildNewUser().build(), adresse))).isFalse();
        assertThat(customer.equals(new Customer(user, Adresse.create(UUID.randomUUID())))).isFalse();
        assertThat(customer.equals(new Customer(userFactory.buildNewUser().build(), Adresse.create(UUID.randomUUID())))).isFalse();        
    }    
}
