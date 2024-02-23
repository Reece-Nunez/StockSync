package com.nunezdev.inventory_manager.service;

import com.nunezdev.inventory_manager.entity.Role;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    Role findByName(String name);
}
