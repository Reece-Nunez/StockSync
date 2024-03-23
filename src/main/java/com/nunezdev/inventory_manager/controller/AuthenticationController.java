package com.nunezdev.inventory_manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nunezdev.inventory_manager.entity.AuthenticationResponse;
import com.nunezdev.inventory_manager.entity.User;
import com.nunezdev.inventory_manager.service.AuthenticationService;

@RestController
public class AuthenticationController {

    private final AuthenticationService authSerivce;

    public AuthenticationController(AuthenticationService authSerivce) {
        this.authSerivce = authSerivce;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
        @RequestBody User request
    ) {
        return ResponseEntity.ok(authSerivce.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
        @RequestBody User request
    ) {
        return ResponseEntity.ok(authSerivce.authenticate(request));
    }


}
