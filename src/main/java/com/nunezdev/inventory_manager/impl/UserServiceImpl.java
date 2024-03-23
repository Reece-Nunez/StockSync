package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.User;
import com.nunezdev.inventory_manager.repository.UserRepository;
import com.nunezdev.inventory_manager.service.UserService;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean verifyLogin(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        return foundUser.isPresent();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }
}
