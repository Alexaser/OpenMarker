package com.example.marketOrders.service;


import com.example.marketOrders.repository.CustomerRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CustomerDetailsService implements UserDetailsService {

       private CustomerRepository customerRepository;

    public CustomerDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       return customerRepository.findByEmail(email).map(customer -> User.withUsername(customer.getEmail())
                .password(customer.getPassword())
                .roles(customer.getRole().name()) // Добавляем роль
                .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }
}
