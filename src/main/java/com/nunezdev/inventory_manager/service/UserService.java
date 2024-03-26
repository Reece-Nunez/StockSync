package com.nunezdev.inventory_manager.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.nunezdev.inventory_manager.dto.UserDTO;

@Service
public interface UserService {
    boolean verifyLogin(UserDTO userDTO);
    UserDTO createUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    boolean updateUserRole(Long userId, String role);
    boolean updateUser(UserDTO userDTO);
    Long getUserIdByUsername(String username);
}
