package com.nunezdev.inventory_manager.service;


import com.nunezdev.inventory_manager.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    boolean verifyLogin(UserDTO userDTO);
    UserDTO createUser(UserDTO userDTO);
}
