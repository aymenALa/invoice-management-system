package com.invoice_management_system.controller;

import com.invoice_management_system.dto.AuthenticationRequest;
import com.invoice_management_system.dto.AuthenticationResponse;
import com.invoice_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = userService.authenticate(
            authenticationRequest.getUsername(),
            authenticationRequest.getPassword()
        );
        
        // For now, we'll just return a dummy token
        String token = "dummy_token_" + authentication.getName();
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}