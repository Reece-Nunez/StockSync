package com.nunezdev.inventory_manager.controller;

import com.nunezdev.inventory_manager.dto.LoginRequest;
import com.nunezdev.inventory_manager.dto.LoginResponse;
import com.nunezdev.inventory_manager.dto.PasswordDTO;
import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.entity.Role;
import com.nunezdev.inventory_manager.security.JwtTokenProvider;
import com.nunezdev.inventory_manager.service.RoleService;
import com.nunezdev.inventory_manager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, RoleService roleService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

        Role role = roleService.findByName(userDTO.getRoleName());

        if (role == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        AppUser appUser = userService.createUser(userDTO.getUsername(), role, userDTO.getPassword());
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

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
