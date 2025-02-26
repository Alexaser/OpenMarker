package com.example.marketOrders.service;

import com.example.marketOrders.entity.Customer;
import com.example.marketOrders.repository.CustomerRepository;
import com.example.marketOrders.security.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AuthenticationService {
    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // Добавляем работу с JWT
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);


    public AuthenticationService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(String email, String rawPassword) {
        Customer user = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        }

        //  Генерируем токен и отправляем клиенту
        return jwtUtil.generateToken(user.getEmail(), user.getUuid());
    }
}
