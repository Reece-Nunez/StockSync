package com.nunezdev.inventory_manager.controller;

import com.nunezdev.inventory_manager.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nunezdev.inventory_manager.entity.AuthenticationResponse;
import com.nunezdev.inventory_manager.entity.User;
import com.nunezdev.inventory_manager.service.AuthenticationService;

@RestController
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authSerivce) {
        this.authService = authSerivce;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(authService.authenticate(userDTO));
    }
}
