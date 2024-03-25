package com.nunezdev.inventory_manager.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.Role;
import com.nunezdev.inventory_manager.entity.User;
import com.nunezdev.inventory_manager.repository.UserRepository;
import com.nunezdev.inventory_manager.service.UserService;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean verifyLogin(UserDTO userDTO) {
        Optional<User> foundUser = userRepository.findByUsername(userDTO.getUsername());
        return foundUser.isPresent() && passwordEncoder.matches(userDTO.getPassword(), foundUser.get().getPassword());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Ensure all new users are assigned the role of 'USER' by default
        user.setRole(Role.USER);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public boolean updateUserRole(Long userId, String roleName) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            try {
                Role role = Role.valueOf(roleName.toUpperCase());
                userRepository.save(user);
                return true;
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid role name: " + roleName);
            }
        }
        return false;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        Optional<User> userOpt = userRepository.findById(userDTO.getId());
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setEmail(userDTO.getEmail());
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
