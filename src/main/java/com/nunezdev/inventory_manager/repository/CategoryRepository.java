package com.nunezdev.inventory_manager.repository;

import com.nunezdev.inventory_manager.dto.CategoryDTO;
import com.nunezdev.inventory_manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE %:searchTerm%")
    List<CategoryDTO> searchCategories(String searchTerm);
}
