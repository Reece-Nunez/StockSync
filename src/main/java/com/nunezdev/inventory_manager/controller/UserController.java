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
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    private Logger logger = org.slf4j.LoggerFactory.getLogger(UserController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, RoleService roleService, JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
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

    @Transactional
    @PutMapping("/{id}/password")
    public ResponseEntity<?> setPassword(@PathVariable long id, @RequestBody PasswordDTO passwordDTO) {
        try {
            logger.info("Attempting to set password for user ID: {}", id);
            AppUser currentUser = userService.getCurrentUser();

            if (currentUser == null) {
                logger.error("No current user found");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to change the password");
            }

            logger.info("Current user found with ID: {}", currentUser.getId());

            if (currentUser.getId() != id && !currentUser.hasRole("ADMIN")) {
                logger.error("User ID does not match or user is not an ADMIN");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to change the password");
            }

            if (!currentUser.hasRole("ADMIN")) {
                if (!passwordEncoder.matches(passwordDTO.getOldPassword(), currentUser.getPassword())) {
                    logger.error("Current password does not match for user ID: {}", currentUser.getId());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current password does not match.");
                }
            }

            AppUser appUser = userService.findById(id);
            if (appUser == null) {
                logger.error("User not found with ID: {}", id);
                return ResponseEntity.notFound().build();
            }

            userService.changePassword(appUser, passwordDTO.getNewPassword());
            logger.info("Password successfully changed for user ID: {}", appUser.getId());
            return ResponseEntity.ok().build();
        } catch (DataAccessException dae) {
            logger.error("Database access error occurred while changing password for user ID: {}", id, dae);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database access error occurred: " + dae.getMessage());
        } catch (Exception e) {
            logger.error("An error occurred while setting the password for user ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while setting the password");
        }
    }


    @PostMapping("/auth/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new LoginResponse(jwt));
        } catch (BadCredentialsException e) {
            // For debugging purposes only; remove this for production!
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + e.getMessage());
        } catch (DisabledException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Account is disabled: " + e.getMessage());
        } catch (AuthenticationException e) {
            // General authentication exception
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            AppUser currentUser = userService.findByUsername(loginRequest.getUsername());

            Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isSelfDeletion = currentAuthentication.getName().equals(currentUser.getUsername());

            if (!isSelfDeletion) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You do not have permission to delete this user");
            }

            userService.deleteUser(currentUser.getId());
            return ResponseEntity.ok().body("User successfully deleted");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials: " + e.getMessage());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        }catch (Exception e) {
            logger.error("An error occurred while deleting user with username: {}", loginRequest.getUsername(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting user");
        }
    }
}
