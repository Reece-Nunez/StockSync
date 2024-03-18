package com.nunezdev.inventory_manager.controller;

import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        if(createdUser != null) {
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> verifyLogin(@RequestBody UserDTO userDTO) {
        boolean isValidUser = userService.verifyLogin(userDTO);
        if(isValidUser) {
            return new ResponseEntity<>(isValidUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(isValidUser, HttpStatus.BAD_REQUEST);
        }
    }
}
