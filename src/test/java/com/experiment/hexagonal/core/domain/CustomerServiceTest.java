package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.spi.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Optional;

import static com.experiment.hexagonal.core.domain.ResultAssert.assertThat;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    @Mock
    private User user;
    
    @Mock
    private Adresse adresse;
    
    @Mock
    private CustomerRepository customerRepository;
    
    @InjectMocks
    private CustomerService customerService;

    @Captor
    ArgumentCaptor<Customer> customerCaptor;

    @Test
    public void doitCreerUnCustomer() {
        // GIVEN
        when(customerRepository.get(user, adresse))
                .thenReturn(Optional.empty());

        // WHEN
        Result<?> result = customerService.createCustomer(user, adresse);

        // THEN
        assertThat(result).isSuccess();

        verify(customerRepository).put(customerCaptor.capture());
        assertThat(customerCaptor.getValue())
                .extracting(
                        Customer::getUser,
                        Customer::getAdresse)
                .containsOnly(
                        user,
                        adresse);
    }
    
    @Test
    public void neDoitPasCreerUnCustomerExistant() {
        // GIVEN
        avecUnCustomer(user, adresse);

        // WHEN
        Result<?> result = customerService.createCustomer(user, adresse);

        // THEN
        assertThat(result).isForbidden();

        verify(customerRepository, never()).put(any(Customer.class));
    }
    
    @Test
    public void doitMettreAJourLUserDUnCustomer() {
        // GIVEN
        Customer customer = avecUnCustomer(user, adresse);

        User newUser = mock(User.class);
        when(customerRepository.get(newUser, adresse))
                .thenReturn(Optional.empty());

        // WHEN
        Result<?> result = customerService.updateUser(customer, newUser);

        // THEN
        assertThat(result).isSuccess();

        verify(customerRepository).put(customerCaptor.capture());
        assertThat(customerCaptor.getValue())
                .extracting(
                        Customer::getUser,
                        Customer::getAdresse)
                .containsOnly(
                        newUser,
                        adresse);
    }
    
    @Test
    public void neDoitMettreAJourLUserDUnCustomerExistant() {
        // GIVEN
        Customer customer = avecUnCustomer(user, adresse);

        User newUser = mock(User.class);

        when(customerRepository.get(eq(newUser), eq(adresse)))
                .thenReturn(Optional.of(customer));

        // WHEN
        Result<?> result = customerService.updateUser(customer, newUser);

        // THEN
        assertThat(result).isForbidden();

        verify(customerRepository, never()).put(any(Customer.class));
    }
    
    @Test
    public void doitMettreAJourLAdresseDUnCustomer() {
        // GIVEN
        Customer customer = avecUnCustomer(user, adresse);

        Adresse newAdresse = mock(Adresse.class);

        when(customerRepository.get(eq(user), eq(newAdresse)))
                .thenReturn(Optional.empty());

        // WHEN
        Result<?> result = customerService.updateAdresse(customer, newAdresse);

        // THEN
        assertThat(result).isSuccess();

        verify(customerRepository).put(customerCaptor.capture());
        assertThat(customerCaptor.getValue())
                .extracting(
                        Customer::getUser,
                        Customer::getAdresse)
                .containsOnly(
                        user,
                        newAdresse);
    }
    
    @Test
    public void neDoitMettreAJourLAdresseDUnCustomerExistant() {
        // GIVEN
        Customer customer = avecUnCustomer(user, adresse);

        Adresse newAdresse = mock(Adresse.class);

        when(customerRepository.get(eq(user), eq(newAdresse)))
                .thenReturn(Optional.of(customer));

        // WHEN
        Result<?> result = customerService.updateAdresse(customer, newAdresse);

        // THEN
        assertThat(result).isForbidden();

        verify(customerRepository, never()).put(any(Customer.class));
    }
    
    @Test
    public void doitEffacerUnCustomer() {
        // GIVEN
        avecUnCustomer(user, adresse);

        // WHEN
        Result<?> result = customerService.deleteCustomer(user, adresse);

        // THEN
        assertThat(result).isSuccess();

        verify(customerRepository).remove(customerCaptor.capture());
        assertThat(customerCaptor.getValue())
                .extracting(
                        Customer::getUser,
                        Customer::getAdresse)
                .containsOnly(
                        user,
                        adresse);
    }
    
    @Test
    public void doitTrouverLesCustomerParUser() {
        // GIVEN
        Customer customer = avecUnCustomer(user, adresse);

        when(customerRepository.getByUser(user))
                .thenReturn(singletonList(customer));

        // WHEN
        Collection<Customer> customers = customerService.findByUser(user);

        // THEN
        assertThat(customers).hasSize(1);
        assertThat(customers)
                .extracting(
                        Customer::getUser,
                        Customer::getAdresse)
                .containsOnly(
                        tuple(user, adresse));  
    }
    
    @Test
    public void doitTrouverLesCustomerParAdresse() {
        // GIVEN
        Customer customer = avecUnCustomer(user, adresse);

        when(customerRepository.getByAdresse(adresse))
                .thenReturn(singletonList(customer));

        // WHEN
        Collection<Customer> customers = customerService.findByAdresse(adresse);

        // THEN
        assertThat(customers).hasSize(1);
        assertThat(customers)
                .extracting(
                        Customer::getUser,
                        Customer::getAdresse)
                .containsOnly(
                        tuple(user, adresse));
    }


    private Customer avecUnCustomer(User user, Adresse adresse) {
        Customer customer = new Customer(user, adresse);
        when(customerRepository.get(user, adresse))
                .thenReturn(Optional.of(customer));
        return customer;
    }
}
