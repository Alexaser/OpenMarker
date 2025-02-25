package com.example.marketOrders.controller;

import com.example.marketOrders.DTO.RegistrationDTO;
import com.example.marketOrders.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationDTO registrationDTO) {
        String response = registrationService.register(

                registrationDTO.getEmail(),
                registrationDTO.getPassword(),
                registrationDTO.getName(),
                registrationDTO.getPhone()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
