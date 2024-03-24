package com.nunezdev.inventory_manager.controller;

import com.nunezdev.inventory_manager.dto.ProductDTO;
import com.nunezdev.inventory_manager.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'OWNER')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        log.info("Fetching all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'OWNER')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        log.info("Fetching product with id: {}", id);
        ProductDTO productDTO = productService.getProductById(id);
        if (productDTO != null) {
            return ResponseEntity.ok(productDTO);
        } else {
            log.info("Product not found with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'OWNER')")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        log.info("Creating new product: {}", productDTO.getName());
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        log.info("Updating product with id: {}", id);
        try {
            ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
            return ResponseEntity.ok(updatedProduct);
        } catch (EntityNotFoundException e) {
            log.error("Product not found with id: {}", id, e);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER')")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        log.info("Deleting product with id: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
