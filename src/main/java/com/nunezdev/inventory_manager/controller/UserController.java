package com.nunezdev.inventory_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'OWNER')")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        if(createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'OWNER')")
    public ResponseEntity<Boolean> verifyLogin(@RequestBody UserDTO userDTO) {
        boolean isValidUser = userService.verifyLogin(userDTO);
        if(isValidUser) {
            return new ResponseEntity<>(isValidUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(isValidUser, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Void> updateUserRole(@PathVariable Long userId, @RequestBody UserDTO userDto) {
        boolean updated = userService.updateUserRole(userId, userDto.getRole());
        if(updated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
