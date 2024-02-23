package Impl;

import Dto.UserDTO;
import Entity.AppUser;
import Repository.UserRepository;
import Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AppUser createUser(String username, String role) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setRole(role);
        userRepository.save(appUser);
        return appUser;
    }

    @Override
    public void changePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(newPassword);
        userRepository.save(appUser);
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
