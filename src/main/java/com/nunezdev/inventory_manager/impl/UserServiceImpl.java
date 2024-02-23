package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.entity.AppUser;
import com.nunezdev.inventory_manager.entity.Role;
import com.nunezdev.inventory_manager.service.UserService;
import com.nunezdev.inventory_manager.dto.UserDTO;
import com.nunezdev.inventory_manager.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppUser createUser(String username, Role role, String password) {
        AppUser appUser = new AppUser();
        appUser.setUsername(username);
        appUser.setRole(role);
        appUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(appUser);
    }

    @Override
    public void changePassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(appUser);
    }

    @Override
    public AppUser getCurrentUser() {
        return userRepository.findByUsername("admin")
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: admin"));
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                true, true, true, true,
                getAuthorities(appUser));

    }

    private Collection<? extends GrantedAuthority> getAuthorities(AppUser appUser) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (appUser.getRole() != null) {
            authorities.add(new SimpleGrantedAuthority(appUser.getRole().getName()));
        }
        return authorities;
    }
}
