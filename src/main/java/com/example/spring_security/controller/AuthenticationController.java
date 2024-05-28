package com.example.spring_security.controller;

import com.example.spring_security.auth.AuthenticatedResponse;
import com.example.spring_security.auth.AuthenticationRequest;
import com.example.spring_security.auth.RegisterRequest;
import com.example.spring_security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticatedResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.register(request));
    }
    @PostMapping("/adm-register")
    public ResponseEntity<AuthenticatedResponse> admRegister(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(service.adminRegister(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticatedResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }


}
