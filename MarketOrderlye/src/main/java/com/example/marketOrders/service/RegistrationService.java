package com.example.marketOrders.service;

import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@Transactional
public class RegistrationService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    public RegistrationService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(String email, String rawPassword, String name, String phone) {
        if (customerRepository.findByEmail(email).isPresent()) {
            logger.warn("Registration attempt failed: Email '{}' is already in use.", email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is already taken.");
        }

        if (customerRepository.findByPhone(phone).isPresent()) {
            logger.warn("Registration attempt failed: Phone '{}' is already in use.", email);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone is already taken.");
        }
        String hashedPassword = passwordEncoder.encode(rawPassword);
        Customer customer = new Customer();

        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setPassword(hashedPassword);

        customerRepository.save(customer);

        logger.info("New user registered successfully: name='{}', email='{}', phone='{}' ", name, email, phone);
        return "User registered successfully!";
    }

}
