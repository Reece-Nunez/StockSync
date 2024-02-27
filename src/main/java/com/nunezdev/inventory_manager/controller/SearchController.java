package com.nunezdev.inventory_manager.controller;

import com.nunezdev.inventory_manager.dto.CategoryDTO;
import com.nunezdev.inventory_manager.dto.ProductDTO;
import com.nunezdev.inventory_manager.entity.Category;
import com.nunezdev.inventory_manager.repository.CategoryRepository;
import com.nunezdev.inventory_manager.repository.ProductRepository;
import com.nunezdev.inventory_manager.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/search")
public class SearchController {
    private final SearchService searchService;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    @Autowired
    public SearchController(SearchService searchService, CategoryRepository categoryRepository, ModelMapper modelMapper, ProductRepository productRepository) {
        this.searchService = searchService;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Object> search(@RequestParam("term") String searchTerm) {
        return searchService.searchAll(searchTerm);
    }

    @GetMapping("/categories")
    public List<CategoryDTO> searchCategories(@RequestParam String searchTerm) {
        List<CategoryDTO> categories = categoryRepository.searchCategories(searchTerm);
        return categories.stream()
             .map(category -> modelMapper.map(category, CategoryDTO.class))
             .collect(Collectors.toList());
    }

    @GetMapping("/products")
    public List<ProductDTO> searchProducts(@RequestParam String searchTerm) {
        List<ProductDTO> categories = productRepository.searchByProductName(searchTerm);
        return categories.stream()
                .map(category -> modelMapper.map(category, ProductDTO.class))
                .collect(Collectors.toList());
    }
}