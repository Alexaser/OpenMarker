package com.example.marketOrders.controller;

import com.example.marketOrders.DTO.LoginRequest;
import com.example.marketOrders.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // TODO : @Valid указать перед @RequestBody
    @PostMapping("/login")
    public ResponseEntity<String> login( @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticate(request.getEmail(), request.getPassword()));
    }
}
