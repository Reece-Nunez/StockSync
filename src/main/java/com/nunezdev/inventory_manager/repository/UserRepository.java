package com.nunezdev.inventory_manager.repository;

import com.nunezdev.inventory_manager.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
