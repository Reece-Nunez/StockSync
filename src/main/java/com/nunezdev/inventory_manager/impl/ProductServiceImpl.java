package com.nunezdev.inventory_manager.impl;

import com.nunezdev.inventory_manager.dto.ProductDTO;
import com.nunezdev.inventory_manager.entity.Product;
import com.nunezdev.inventory_manager.repository.ProductRepository;
import com.nunezdev.inventory_manager.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
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
        validateProductDTO(productDto);
        Product product = modelMapper.map(productDto, Product.class);

        if (product.getDateCreated() == null) {
            product.setDateCreated(ZonedDateTime.now());
        }

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id: " + id + " not found"));

        validateProductDTO(productDTO);
        modelMapper.map(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    private void validateProductDTO(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
        if (productDTO.getPrice() == null) {
            throw new IllegalArgumentException("Product price cannot be null");
        }
        if (productDTO.getQuantity() == null) {
            throw new IllegalArgumentException("Product quantity cannot be null");
        }

    }


    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private ProductDTO convertToDto(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> searchByProductName(String productName) {
        return productRepository.findByProductNameContainingIgnoreCase(productName)
          .stream()
          .map(product -> modelMapper.map(product, ProductDTO.class))
          .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchProducts(String searchTerm) {
        return productRepository.searchByTerm(searchTerm)
               .stream()
               .map(product -> modelMapper.map(product, ProductDTO.class))
               .collect(Collectors.toList());
    }
}
