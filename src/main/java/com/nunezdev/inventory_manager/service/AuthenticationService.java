package com.nunezdev.inventory_manager.service;

import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.exception.CustomAuthenticationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nunezdev.inventory_manager.entity.AuthenticationResponse;
import com.nunezdev.inventory_manager.entity.User;
import com.nunezdev.inventory_manager.repository.UserRepository;

@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        user = repository.save(user);

        String token = jwtService.generateToken(user);
        String role = user.getRole().name();
        String name = user.getFirstName();

        return new AuthenticationResponse(token, role, name);
    }

    public AuthenticationResponse authenticate(UserDTO userDTO) {
        try {
            // Attempt authentication with the provided credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
            );

            // Look up the fully populated User object from the database
            User user = repository.findByUsername(userDTO.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userDTO.getUsername()));

            // Generate a JWT token for the authenticated user
            String token = jwtService.generateToken(user);
            // Get Role name
            String role = user.getRole().name();
            String name = user.getFirstName();

            // Return the token encapsulated in an AuthenticationResponse
            return new AuthenticationResponse(token, role, name);
        } catch (AuthenticationException e) {
            // Log the error and return an appropriate response
            logger.error("Authentication failed for user: " + userDTO.getUsername(), e);
            throw new CustomAuthenticationFailedException("Authentication failed", e);
        }
    }
}
