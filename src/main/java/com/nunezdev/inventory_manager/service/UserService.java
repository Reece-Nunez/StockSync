package com.nunezdev.inventory_manager.service;


import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.entity.Role;
import com.nunezdev.inventory_manager.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    AppUser createUser(String username, Role role, String rawPassword);

    void changePassword(AppUser appUser, String newPassword);
    AppUser findById(long id);
    UserDTO convertToDTO(AppUser appUser);
    AppUser convertToEntity(UserDTO userDTO);
    AppUser getCurrentUser();
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
