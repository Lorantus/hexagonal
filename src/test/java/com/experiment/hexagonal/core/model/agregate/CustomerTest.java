package com.experiment.hexagonal.core.model.agregate;

import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.model.valueobject.Password;
import org.junit.Test;

import java.util.UUID;

import static com.experiment.hexagonal.core.factory.UserBuilder.buildNewUser;
import static com.experiment.hexagonal.core.model.agregate.AdressBuilder.createAdressBuilder;
import static com.experiment.hexagonal.core.model.agregate.CustomerBuilder.creatCustomerBuilder;
import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTest {
    
    @Test
    public void doitRetournerCustomerAvecUnNouveauUtilisateur() {
        // GIVEN
        Customer customer = creatCustomerBuilder()
                .withUser(buildNewUser("login", Password.create("password"), "développeur"))
                .withAdresse(createAdressBuilder()
                        .withVille("ville"))
                .build();

        User nouveauUser = buildNewUser("login", Password.create("password"), "développeur")
                .build();

        // WHEN
        Customer newCustomer = customer.withUser(nouveauUser);
        
        // THEN
        assertThat(newCustomer)
                .extracting(Customer::getUser, Customer::getAdresse)
                .containsExactly(nouveauUser, customer.getAdresse());
    }

    @Test
    public void doitRetournerCustomerAvecUneNouvelleAdresse() {
        // GIVEN
        Customer customer = creatCustomerBuilder()
                .withUser(buildNewUser("login", Password.create("password"), "développeur"))
                .withAdresse(createAdressBuilder()
                        .withVille("ancienne ville"))
                .build();

        Adresse nouvelleAdresse = Adresse.create(UUID.randomUUID(), "nouvelle ville");

        // WHEN
        Customer newCustomer = customer.withAdresse(nouvelleAdresse);

        // THEN
        assertThat(newCustomer)
                .extracting(Customer::getUser, Customer::getAdresse)
                .containsExactly(customer.getUser(), nouvelleAdresse);
    }

    @Test
    public void testEquals() {
        // GIVEN
        Customer customer = creatCustomerBuilder()
                .withUser(buildNewUser("login", Password.create("password"), "développeur"))
                .withAdresse(createAdressBuilder()
                        .withVille("ville"))
                .build();

        User user = customer.getUser();
        Adresse adresse = customer.getAdresse();

        // WHEN - THEN
        assertThat(customer.equals(new Customer(user, adresse))).isTrue();

        assertThat(customer.equals(new Customer(buildNewUser("login", Password.create("password"), "développeur").build(), adresse))).isFalse();
        assertThat(customer.equals(new Customer(user, Adresse.create(UUID.randomUUID(), "ville")))).isFalse();
        assertThat(customer.equals(new Customer(buildNewUser("login", Password.create("password"), "développeur").build(), Adresse.create(UUID.randomUUID(), "ville")))).isFalse();
    }    
}
