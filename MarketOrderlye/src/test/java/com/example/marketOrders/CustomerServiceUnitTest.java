package com.example.marketOrders;


import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.repository.CustomerRepository;
import com.example.marketOrders.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerServiceUnitTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    public CustomerServiceUnitTest() {
        MockitoAnnotations.openMocks(this);
    }

    // Проверка как работает метод поиска по id
    @Test
    public void testFindById() {
        Customer customer = new Customer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer customer1 = customerService.findById(1L);
        assertNotNull(customer1);
        assertEquals(customer, customer1);
    }

    // проверка работает ли чек валидности mail
    @Test
    public void testValidMail() {
        Customer customer = new Customer();

        customer.setEmail("invalid.Email");
        customer.setId(1L);
        customer.setName("Test Name");
        customer.setPhone("79833234792");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                customerService.save(customer));
        assertEquals("Invalid email format", exception.getMessage());
    }


    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setEmail("email@set.com");
        customer.setId(1L);
        customer.setName("Test Name");
        customer.setPhone("79833234792");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer saveCustomer = customerService.save(customer);

        assertNotNull(saveCustomer);

        assertEquals(customer.getName(), saveCustomer.getName());
        assertEquals(customer.getEmail(), saveCustomer.getEmail());
        assertEquals(customer.getPhone(), saveCustomer.getPhone());
        assertEquals(customer.getId(), saveCustomer.getId());

        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setEmail("email@set.com");
        customer.setId(1L);
        customer.setName("Test Name");
        customer.setPhone("70983323472");
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        assertEquals("Customer update", customerService.updateCustomer(customer, 1L));
    }

    @Test
    public void testDeleteCustomer_Success() {
        Long customerId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(true);
        customerService.deleteCustomer(customerId);
        verify(customerRepository, times(1)).deleteCustomerById(customerId);
    }

    @Test
    public void testDeleteCustomer_CustomerNotFound() {
        Long customerId = 1L;

        when(customerRepository.existsById(customerId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                customerService.deleteCustomer(customerId));

        assertEquals("Customer not found", exception.getMessage());
    }

}
