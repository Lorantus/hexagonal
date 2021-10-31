package com.experiment.hexagonal.core.domain;

import com.experiment.hexagonal.core.api.transaction.Result;
import com.experiment.hexagonal.core.api.transaction.ResultType;
import com.experiment.hexagonal.core.model.aggregate.Customer;
import com.experiment.hexagonal.core.model.entity.Adresse;
import com.experiment.hexagonal.core.model.entity.User;
import com.experiment.hexagonal.core.spi.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;
import java.util.Optional;

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
    ArgumentCaptor<Customer> argument;
    
    private Customer customer;
    
    @Before
    public void setup() {
        customer = new Customer(user, adresse);
        when(customerRepository.get(eq(user), eq(adresse)))
                .thenReturn(Optional.of(customer));
    }
    
    @Test
    public void doitCreerUnCustomer() {
        // GIVEN
        when(customerRepository.get(eq(user), eq(adresse)))
                .thenReturn(Optional.empty());

        // WHEN
        Result<?> result = customerService.createCustomer(user, adresse);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);

        verify(customerRepository).put(argument.capture());
        assertThat(argument.getValue())
                .extracting(
                        Customer::getUser,
                        Customer::getAdresse)
                .containsOnly(
                        user,
                        adresse);
    }
    
    @Test
    public void neDoitPasCreerUnCustomerExistant() {
        // WHEN
        Result<?> result = customerService.createCustomer(user, adresse);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);
        
        verify(customerRepository, never()).put(any(Customer.class));     
    }
    
    @Test
    public void doitMettreAJourLUserDUnCustomer() {
        // GIVEN        
        User newUser = mock(User.class);

        when(customerRepository.get(eq(newUser), eq(adresse)))
                .thenReturn(Optional.empty());

        // WHEN
        Result<?> result = customerService.updateUser(customer, newUser);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);

        verify(customerRepository).put(argument.capture());
        assertThat(argument.getValue())
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
        User newUser = mock(User.class);

        when(customerRepository.get(eq(newUser), eq(adresse)))
                .thenReturn(Optional.of(customer));

        // WHEN
        Result<?> result = customerService.updateUser(customer, newUser);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);

        verify(customerRepository, never()).put(any(Customer.class));
    }
    
    @Test
    public void doitMettreAJourLAdresseDUnCustomer() {
        // GIVEN        
        Adresse newAdresse = mock(Adresse.class);

        when(customerRepository.get(eq(user), eq(newAdresse)))
                .thenReturn(Optional.empty());

        // WHEN
        Result<?> result = customerService.updateAdresse(customer, newAdresse);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);

        verify(customerRepository).put(argument.capture());
        assertThat(argument.getValue())
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
        Adresse newAdresse = mock(Adresse.class);

        when(customerRepository.get(eq(user), eq(newAdresse)))
                .thenReturn(Optional.of(customer));

        // WHEN
        Result<?> result = customerService.updateAdresse(customer, newAdresse);

        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.FORBIDDEN);

        verify(customerRepository, never()).put(any(Customer.class));
    }
    
    @Test
    public void doitEffacerUnCustomer() {
        // WHEN
        Result<?> result = customerService.deleteCustomer(user, adresse);
        
        // THEN
        assertThat(result.getResultType()).isEqualTo(ResultType.OK);
        
        verify(customerRepository).remove(argument.capture());
        assertThat(argument.getValue())
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
}
