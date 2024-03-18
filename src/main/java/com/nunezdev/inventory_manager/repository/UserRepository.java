package com.nunezdev.inventory_manager.repository;

import com.nunezdev.inventory_manager.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsernameAndPassword(String username, String password);
}
