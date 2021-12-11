package com.experiment.hexagonal.core.model.agregate;

import com.experiment.hexagonal.core.factory.UserBuilder;
import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.valueobject.Password;
import org.junit.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAdresseTest {
    
    @Test
    public void doitRetournerUserAdresseAvecUnNouveauUtilisateur() {
        // GIVEN
        User user = UserBuilder.buildNewUser("login", Password.create("password"), "développeur").build();
        Adresse adresse = Adresse.create(UUID.randomUUID(), "ville");

        Customer customer = new Customer(user, adresse);

        User nouveauUser = UserBuilder.buildNewUser("login", Password.create("password"), "développeur")
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
        User user = UserBuilder.buildNewUser("login", Password.create("password"), "développeur")
                .build();
        Adresse adresse = Adresse.create(UUID.randomUUID(), "ancienne ville");

        Customer customer = new Customer(user, adresse);

        Adresse nouvelleAdresse = Adresse.create(UUID.randomUUID(), "nouvelle ville");

        // WHEN
        Customer newCustomer = customer.withAdresse(nouvelleAdresse);

        // THEN
        assertThat(newCustomer)
                .extracting(Customer::getUser, Customer::getAdresse)
                .containsExactly(user, nouvelleAdresse);
    }

    @Test
    public void testEquals() {
        // GIVEN
        User user = UserBuilder.buildNewUser("login", Password.create("password"), "développeur").build();
        Adresse adresse = Adresse.create(UUID.randomUUID(), "ville");

        Customer customer = new Customer(user, adresse);

        // WHEN - THEN
        assertThat(customer.equals(new Customer(user, adresse))).isTrue();

        assertThat(customer.equals(new Customer(UserBuilder.buildNewUser("login", Password.create("password"), "développeur").build(), adresse))).isFalse();
        assertThat(customer.equals(new Customer(user, Adresse.create(UUID.randomUUID(), "ville")))).isFalse();
        assertThat(customer.equals(new Customer(UserBuilder.buildNewUser("login", Password.create("password"), "développeur").build(), Adresse.create(UUID.randomUUID(), "ville")))).isFalse();
    }    
}
