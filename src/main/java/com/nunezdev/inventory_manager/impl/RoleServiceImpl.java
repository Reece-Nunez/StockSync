package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.entity.Role;
import com.nunezdev.inventory_manager.repository.RoleRepository;
import com.nunezdev.inventory_manager.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with name: " + name));

    }
}
