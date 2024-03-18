package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.repository.UserRepository;
import com.nunezdev.inventory_manager.service.UserService;
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
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).isPresent();
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        AppUser user = modelMapper.map(userDTO, AppUser.class);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }
}
