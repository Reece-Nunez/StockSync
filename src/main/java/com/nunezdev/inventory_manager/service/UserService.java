package com.nunezdev.inventory_manager.service;


import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.AppUser;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    AppUser createUser(String username, String password);
    void changePassword(AppUser appUser, String newPassword);
    AppUser findById(long id);
    UserDTO convertToDTO(AppUser appUser);
    AppUser convertToEntity(UserDTO userDTO);
}
