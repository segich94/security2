package com.example.spring_security.service;

import com.example.spring_security.auth.AuthenticatedResponse;
import com.example.spring_security.auth.AuthenticationRequest;
import com.example.spring_security.auth.RegisterRequest;
import com.example.spring_security.user.Role;
import com.example.spring_security.user.User;
import com.example.spring_security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticatedResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticatedResponse.builder().token(jwtToken).build();
    }

    public AuthenticatedResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(()->new UsernameNotFoundException("not found"));
        var jwtToken = jwtService.generateToken(user);
        return  AuthenticatedResponse.builder().token(jwtToken).build();
    }
}
