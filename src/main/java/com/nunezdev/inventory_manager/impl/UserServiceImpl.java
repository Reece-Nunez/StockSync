package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.service.UserService;
import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AppUser createUser(String username, String role, String rawPassword) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setRole(role);
        appUser.setPassword(passwordEncoder.encode(rawPassword));
        userRepository.save(appUser);
        return appUser;
    }

    @Override
    public void changePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(appUser);
    }

    @Override
    public AppUser getCurrentUser() {
        return userRepository.findByUsername("admin");
    }

    @Override
    public AppUser findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO convertToDTO(AppUser appUser) {
        return modelMapper.map(appUser, UserDTO.class);
    }

    public AppUser convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, AppUser.class);
    }

    public AppUser loadUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
