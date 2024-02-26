package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.dto.ProductDTO;
import com.nunezdev.inventory_manager.entity.Product;
import com.nunezdev.inventory_manager.repository.ProductRepository;
import com.nunezdev.inventory_manager.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .orElse(null);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(product -> {
                    // Map the DTO to the existing entity
                    modelMapper.map(productDTO, product);
                    // Save the updated entity
                    Product updatedProduct = productRepository.save(product);
                    // Return the updated DTO
                    return modelMapper.map(updatedProduct, ProductDTO.class);
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
