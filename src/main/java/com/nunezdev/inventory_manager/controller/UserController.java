package com.nunezdev.inventory_manager.controller;

import com.nunezdev.inventory_manager.dto.PasswordDTO;
import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        AppUser appUser = userService.createUser(userDTO.getUsername(), userDTO.getRole(), userDTO.getPassword());
        UserDTO createdUser = userService.convertToDTO(appUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<?> setPassword(@PathVariable long id, @RequestBody PasswordDTO passwordDTO) {
        try {
            AppUser currentUser = userService.getCurrentUser();
            if (currentUser == null || (currentUser.getId() != id && !currentUser.hasRole("ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to change the password");
            }

            AppUser appUser = userService.findById(id);
            if (appUser == null) {
                return ResponseEntity.notFound().build();
            }

            userService.changePassword(appUser, passwordDTO.getNewPassword());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while setting the password");
        }
    }
}
