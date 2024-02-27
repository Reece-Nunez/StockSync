package com.nunezdev.inventory_manager.repository;

import com.nunezdev.inventory_manager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :searchTerm, '%')) OR LOWER (p.description) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<Product> searchByTerm(String searchTerm);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<Product> findByProductNameContainingIgnoreCase(String searchTerm);

    @Query("SELECT p FROM Product p WHERE LOWER(p.category.name) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<Product> findByCategoryNameContainingIgnoreCase(String searchTerm);
}
