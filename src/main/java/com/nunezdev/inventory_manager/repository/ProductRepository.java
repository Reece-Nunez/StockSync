package com.nunezdev.inventory_manager.repository;

import com.nunezdev.inventory_manager.dto.ProductDTO;
import com.nunezdev.inventory_manager.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :searchTerm, '%')) OR LOWER (p.description) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<ProductDTO> searchByTerm(String searchTerm);

    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<ProductDTO> findByProductNameContainingIgnoreCase(String searchTerm);

    @Query("SELECT p FROM Product p WHERE LOWER(p.category.name) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<ProductDTO> findByCategoryNameContainingIgnoreCase(String searchTerm);

    @Query("SELECT p FROM Product p WHERE LOWER(p.description) LIKE LOWER(concat('%', :searchTerm, '%'))")
    List<ProductDTO> searchByProductName(String searchTerm);
}
